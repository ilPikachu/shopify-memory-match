package com.example.qiyuanbao.memorymatch.ui.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.qiyuanbao.memorymatch.data.database.getDateBase
import com.example.qiyuanbao.memorymatch.data.repository.ProductImagesRepository
import com.example.qiyuanbao.memorymatch.data.repository.UserScoreRepository

@Suppress("UNCHECKED_CAST")
class GameViewModelFactory(
    private val application: Application,
    private val gridSize: Int,
    private val matchPairs: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            val productImagesRepository = ProductImagesRepository(getDateBase(application))
            val userScoreRepository = UserScoreRepository(getDateBase(application))

            return GameViewModel(productImagesRepository, userScoreRepository, gridSize, matchPairs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}