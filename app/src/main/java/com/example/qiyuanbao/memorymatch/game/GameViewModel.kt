package com.example.qiyuanbao.memorymatch.game

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.qiyuanbao.memorymatch.database.getDateBase
import com.example.qiyuanbao.memorymatch.extension.notifyObserver
import com.example.qiyuanbao.memorymatch.model.ProductImage
import com.example.qiyuanbao.memorymatch.model.Status
import com.example.qiyuanbao.memorymatch.model.UserScore
import com.example.qiyuanbao.memorymatch.repository.ProductImagesRepository
import com.example.qiyuanbao.memorymatch.repository.UserScoreRepository
import kotlinx.coroutines.launch
import java.io.IOException

class GameViewModel(
    application: Application,
    private val gridSize: Int,
    private val matchPairs: Int
) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "GameViewModel"
    }

    private val productImagesRepository = ProductImagesRepository(getDateBase(application))
    private val userScoreRepository = UserScoreRepository(getDateBase(application))

    private val _pairsFound = MutableLiveData(0)
    val pairsFound: LiveData<Int>
        get() = _pairsFound

    private val _userScore = MutableLiveData(UserScore(0))
    val userScore: LiveData<UserScore>
        get() = _userScore

    private val _productImages = MutableLiveData<List<ProductImage>>()
    val productImages: LiveData<List<ProductImage>>
        get() = _productImages

    private val _gameEndEvent = MutableLiveData(false)
    val gameEndEvent: LiveData<Boolean>
        get() = _gameEndEvent

    init {
        getProductDetails()
    }

    private fun getProductDetails() {
        // coroutine to launch with viewModelScope to retreat data from repository
        // This scope is bound to Dispatchers.Main and will be automatically cancelled when ViewModel is cleared
        viewModelScope.launch {
            var productImages = productImagesRepository.getProductImages()

            if (productImages.isEmpty()) {
                try {
                    productImagesRepository.refreshProductImages()
                    productImages = productImagesRepository.getProductImages()
                } catch (networkError: IOException) {
                    Log.e(TAG, "Network error")
                }
            }

            _productImages.value = getFilteredProductImages(productImages)
        }
    }

    private fun getFilteredProductImages(productImages: List<ProductImage>): List<ProductImage> {
        val initialProductImages = productImages
            .shuffled()
            .take(gridSize)

        // deep copy
        val finalProductImages = mutableListOf<ProductImage>()
        repeat(matchPairs) { finalProductImages.addAll(initialProductImages.map { it.copy() }) }

        return finalProductImages.shuffled()
    }

    fun onCardClicked(productImage: ProductImage) {
        /*
             if the current card is flip or match do nothing
             if the current card is await change it to flip
             once it's flip
                check match time with another peer
                check total flipped cards
                    if the difference > 1 match failed, reset all to await except for already matched
                    if the difference == 1 and flippedCardNum == desired match pairs, match all peers
                    if the difference == 1 only, then continue flip the card
        */
        if (isCardFlip(productImage) || isCardMatch(productImage))
        // do nothing
        else if (isCardAwait(productImage)) {
            setCardFlip(productImage)

            val matchedTimes = countCardMarchTimes(productImage)
            val flippedCardsNum = countFlippedCards()
            val difference = flippedCardsNum - matchedTimes

            if (difference > 1)
                setCardsAwait()
            else if (difference == 1 && flippedCardsNum == matchPairs)
                setCardsMatch(productImage)
            else if (difference == 1)
                setCardFlip(productImage)

        }
    }

    fun onShuffle() {
        shuffleAllCards()
    }

    fun onPlayAgain() {
        resetPairsFound()
        resetUserScore()
        resetAllCardsState()
    }

    private fun countCardMarchTimes(productImage: ProductImage): Int {
        var matchedTimes = 0

        _productImages.value?.forEach {
            if (it !== productImage && it.imgSrcUrl == productImage.imgSrcUrl
                && (isCardFlip(it) && isCardFlip(productImage))
            ) {
                matchedTimes++
            }
        }

        return matchedTimes
    }

    private fun countFlippedCards(): Int {
        var count = 0
        _productImages.value?.forEach {
            if (isCardFlip(it)) {
                count++
            }
        }

        return count
    }

    private fun isCardAwait(productImage: ProductImage) = productImage.status == Status.AWAIT

    private fun isCardFlip(productImage: ProductImage) = productImage.status == Status.FLIP

    private fun isCardMatch(productImage: ProductImage) = productImage.status == Status.MATCH

    private fun setCardsAwait() {
        _productImages.value?.forEach {
            if (it.status != Status.MATCH)
                it.status = Status.AWAIT
        }
        _productImages.notifyObserver()
    }

    private fun setCardFlip(productImage: ProductImage) {
        productImage.status = Status.FLIP
        _productImages.notifyObserver()
    }

    private fun setCardsMatch(productImage: ProductImage) {
        _productImages.value?.forEach {
            if (it.imgSrcUrl == productImage.imgSrcUrl) {
                it.status = Status.MATCH
            }
        }
        _productImages.notifyObserver()

        updateUserScore()
        updatePairsFound()

        checkPairsFound()?.let {
            if (it == gridSize) {
                onGameEnd()
            }
        }
    }

    private fun shuffleAllCards() {
        _productImages.value = _productImages.value?.shuffled()
    }

    private fun updatePairsFound() {
        _pairsFound.value = _pairsFound.value?.plus(1)
    }

    // increasing matchPairs difficulty will increase user score per match
    private fun updateUserScore() {
        val score = (matchPairs - 1) * 10

        _userScore.value?.scores?.let {
            _userScore.value?.scores = it + score
            _userScore.notifyObserver()
        }
    }

    private fun resetAllCardsState() {
        _productImages.value?.forEach {
            it.status = Status.AWAIT
        }
        shuffleAllCards()
    }

    private fun checkPairsFound(): Int? = _pairsFound.value

    private fun resetPairsFound() {
        _pairsFound.value = 0
    }

    private fun resetUserScore() {
        _userScore.value?.scores?.let {
            _userScore.value?.scores = 0
            _userScore.notifyObserver()
        }
    }

    private fun onGameEnd() {
        _gameEndEvent.value = true
        _userScore.value?.let {
            submitUserScore(it)
        }
    }

    private fun submitUserScore(userScore: UserScore) {
        viewModelScope.launch {
            userScoreRepository.insertUserScore(userScore)
        }
    }

    fun onGameEndComplete() {
        _gameEndEvent.value = false
    }

}

