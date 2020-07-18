package com.example.qiyuanbao.memorymatch.score

import androidx.lifecycle.*
import com.example.qiyuanbao.memorymatch.model.UserScore
import com.example.qiyuanbao.memorymatch.repository.UserScoreRepository
import kotlinx.coroutines.launch

class ScoreViewModel(private val userScoreRepository: UserScoreRepository) : ViewModel() {

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