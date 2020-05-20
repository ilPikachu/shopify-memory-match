package com.example.qiyuanbao.memorymatch.score

import android.app.Application
import androidx.lifecycle.*
import com.example.qiyuanbao.memorymatch.database.getDateBase
import com.example.qiyuanbao.memorymatch.model.UserScore
import com.example.qiyuanbao.memorymatch.repository.UserScoreRepository
import kotlinx.coroutines.launch

class ScoreViewModel(application: Application) : AndroidViewModel(application) {

    private val userScoreRepository = UserScoreRepository(getDateBase(application))

    private val _userScores = MutableLiveData<List<UserScore>>()
    val userScores: LiveData<List<UserScore>>
        get() = _userScores

    init {
        getUserScores()
    }

    private fun getUserScores() {
        viewModelScope.launch {
            _userScores.value = userScoreRepository.getAllUserScores()
        }
    }

}