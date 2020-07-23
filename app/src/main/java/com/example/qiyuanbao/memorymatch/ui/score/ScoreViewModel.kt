package com.example.qiyuanbao.memorymatch.ui.score

import androidx.lifecycle.*
import com.example.qiyuanbao.memorymatch.data.model.UserScore
import com.example.qiyuanbao.memorymatch.data.repository.UserScoreRepository
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