package com.example.qiyuanbao.memorymatch.repository

import com.example.qiyuanbao.memorymatch.database.AppDatabase
import com.example.qiyuanbao.memorymatch.extension.toDomainModel
import com.example.qiyuanbao.memorymatch.extension.toEntityModel
import com.example.qiyuanbao.memorymatch.model.UserScore

class UserScoreRepository(private val database: AppDatabase) {
    /**
     * [com.example.qiyuanbao.memorymatch.database.dao.UserScoreDao]
     * all DAO methods are both marked as suspend,
     * therefore Room will ensure they are main safe.
     *
     * Note we don't use Dispatcher.IO because Room will use its own dispatcher to run
     * queries in the background thread, using Dispatcher.IO will actually make your queries slower.
     */

    suspend fun getUserScore(uuid: String): UserScore {
        return database.getUserScoreDao().getUserScore(uuid).toDomainModel()
    }

    suspend fun getAllUserScores(): List<UserScore> {
        return database.getUserScoreDao().getAllUserScores().toDomainModel()
    }

    suspend fun insertUserScore(userScore: UserScore) {
        database.getUserScoreDao().insert(userScore.toEntityModel())
    }

}