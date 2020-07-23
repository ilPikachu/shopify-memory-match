package com.example.qiyuanbao.memorymatch.data.database.entitity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userScore")
data class UserScoreEntity (
    val scores: Int,
    @PrimaryKey
    val uuid: String
)