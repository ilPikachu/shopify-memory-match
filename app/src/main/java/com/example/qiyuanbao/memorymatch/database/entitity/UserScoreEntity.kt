package com.example.qiyuanbao.memorymatch.database.entitity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "userScore")
data class UserScoreEntity (
    val scores: Int,
    @PrimaryKey
    val uuid: String
)