package com.example.qiyuanbao.memorymatch.model

import java.util.*

data class UserScore(
    var scores: Int,
    val uuid: String = UUID.randomUUID().toString()
)