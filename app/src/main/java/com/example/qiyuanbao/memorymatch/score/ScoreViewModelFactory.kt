package com.example.qiyuanbao.memorymatch.score

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.qiyuanbao.memorymatch.database.getDateBase
import com.example.qiyuanbao.memorymatch.repository.UserScoreRepository

@Suppress("UNCHECKED_CAST")
class ScoreViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(UserScoreRepository(getDateBase(application))) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}