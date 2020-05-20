package com.example.qiyuanbao.memorymatch.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class GameViewModelFactory(
    private val application: Application,
    private val gridSize: Int,
    private val matchPairs: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(application, gridSize, matchPairs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}