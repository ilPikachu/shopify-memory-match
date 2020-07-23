package com.example.qiyuanbao.memorymatch.data.model

import java.util.*

data class UserScore(
    var scores: Int,
    val uuid: String = UUID.randomUUID().toString()
)