package com.example.qiyuanbao.memorymatch.ui.game

import android.util.Log
import androidx.lifecycle.*
import com.example.qiyuanbao.memorymatch.extension.notifyObserver
import com.example.qiyuanbao.memorymatch.data.model.ProductImage
import com.example.qiyuanbao.memorymatch.data.model.Status
import com.example.qiyuanbao.memorymatch.data.model.UserScore
import com.example.qiyuanbao.memorymatch.data.repository.ProductImagesRepository
import com.example.qiyuanbao.memorymatch.data.repository.UserScoreRepository
import kotlinx.coroutines.launch
import java.io.IOException

class GameViewModel(
    private val productImagesRepository: ProductImagesRepository,
    private val userScoreRepository: UserScoreRepository,
    private val gridSize: Int,
    private val matchPairs: Int
) : ViewModel() {
    // TODO: Convert the game into using HashMap implementation
    companion object {
        private const val TAG = "GameViewModel"
    }

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

    fun onCardClicked(productImage: ProductImage) {
        /*
            1. Set the current card to flip
            2. if current flipped card count is equal to matched pairs, check if they are all matched
                if yes set them all to match
            3. if the current flipped card count is equal to matchPairs + 1 we need to reset all flipped
            except for last clicked card
        */
        if (isCardAwait(productImage)) {
            setCardFlip(productImage)

            val flippedCards = countFlippedCards()

            if (flippedCards == matchPairs && isAllFlippedCardsMatched()) {
                setAllFlippedCardsToMatch()
            } else if (flippedCards == matchPairs + 1) {
                setAllFlippedCardsToAwait()
                setCardFlip(productImage)
            }

            _productImages.notifyObserver()
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

    private fun onGameEnd() {
        _gameEndEvent.value = true
        _userScore.value?.let {
            submitUserScore(it)
        }
    }

    fun onGameEndComplete() {
        _gameEndEvent.value = false
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
        val initialFilteredProductImages = productImages
            .shuffled()
            .take(gridSize)

        // deep copy
        val finalFilteredProductImages = mutableListOf<ProductImage>()
        repeat(matchPairs) { finalFilteredProductImages.addAll(initialFilteredProductImages.map { it.copy() }) }

        return finalFilteredProductImages.shuffled()
    }

    private fun isAllFlippedCardsMatched(): Boolean {
        val currentFlippedCard = getFlippedCards()
        return currentFlippedCard.all { it.imgSrcUrl == currentFlippedCard[0].imgSrcUrl }
    }

    private fun getFlippedCards(): MutableList<ProductImage> {
        val flipCardList = mutableListOf<ProductImage>()
        _productImages.value?.forEach {
            if (isCardFlip(it)) flipCardList.add(it)
        }

        return flipCardList
    }

    private fun countFlippedCards(): Int {
        var count = 0
        _productImages.value?.forEach {
            if (isCardFlip(it)) count++
        }

        return count
    }

    private fun isCardFlip(productImage: ProductImage) = productImage.status == Status.FLIP

    private fun isCardAwait(productImage: ProductImage) = productImage.status == Status.AWAIT

    private fun setCardAwait(productImage: ProductImage) {
        productImage.status = Status.AWAIT
    }

    private fun setCardMatch(productImage: ProductImage) {
        productImage.status = Status.MATCH
    }

    private fun setCardFlip(productImage: ProductImage) {
        productImage.status = Status.FLIP
    }

    private fun setAllFlippedCardsToAwait() {
        _productImages.value?.forEach {
            if (isCardFlip(it)) setCardAwait(it)
        }
    }

    private fun setAllFlippedCardsToMatch() {
        _productImages.value?.forEach {
            if (isCardFlip(it)) setCardMatch(it)
        }

        updateUserScore()
        updatePairsFound()

        if (isGameEnd())
            onGameEnd()
    }

    private fun updatePairsFound() {
        _pairsFound.value = _pairsFound.value?.plus(1)
    }

    private fun checkPairsFound(): Int? = _pairsFound.value

    private fun resetPairsFound() {
        _pairsFound.value = 0
    }

    // increasing matchPairs difficulty will increase user score per match
    private fun updateUserScore() {
        val score = (matchPairs - 1) * 10

        _userScore.value?.scores?.let {
            _userScore.value?.scores = it + score
            _userScore.notifyObserver()
        }
    }

    private fun resetUserScore() {
        _userScore.value?.scores = 0
        _userScore.notifyObserver()
    }

    private fun shuffleAllCards() {
        _productImages.value = _productImages.value?.shuffled()
    }

    private fun resetAllCardsState() {
        _productImages.value?.forEach {
            setCardAwait(it)
        }
        shuffleAllCards()
    }

    private fun isGameEnd(): Boolean = checkPairsFound() == gridSize

    private fun submitUserScore(userScore: UserScore) {
        viewModelScope.launch {
            userScoreRepository.insertUserScore(userScore)
        }
    }

}

