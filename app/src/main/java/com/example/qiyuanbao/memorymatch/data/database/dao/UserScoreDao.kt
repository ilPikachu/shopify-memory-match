package com.example.qiyuanbao.memorymatch.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy.REPLACE
import com.example.qiyuanbao.memorymatch.data.database.entitity.UserScoreEntity

@Dao
interface UserScoreDao {
    @Query("select * from userScore")
    suspend fun getAllUserScores(): List<UserScoreEntity>

    @Query("select * from userScore WHERE uuid = :uuid LIMIT 1")
    suspend fun getUserScore(uuid: String): UserScoreEntity

    @Insert(onConflict = REPLACE)
    suspend fun insert(userScoreEntity: UserScoreEntity)

}