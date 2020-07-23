package com.example.qiyuanbao.memorymatch.data.repository

import com.example.qiyuanbao.memorymatch.data.database.AppDatabase
import com.example.qiyuanbao.memorymatch.extension.toDomainModel
import com.example.qiyuanbao.memorymatch.extension.toEntityModel
import com.example.qiyuanbao.memorymatch.data.model.UserScore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserScoreRepository(private val database: AppDatabase) {
    /**
     * [com.example.qiyuanbao.memorymatch.database.dao.UserScoreDao]
     * all DAO methods are both marked as suspend,
     * therefore Room will ensure they are main safe.
     *
     * Note we could discard Dispatcher.IO because Room will use its own dispatcher to run
     * queries in the background thread, using Dispatcher.IO will actually make your queries slower.
     * https://medium.com/androiddevelopers/coroutines-on-android-part-iii-real-work-2ba8a2ec2f45
     *
     * But I think it's clearer to developers that this is running on IO by withContext(Dispatchers.IO) explicitly
     */

    suspend fun getUserScore(uuid: String): UserScore {
        return withContext(Dispatchers.IO) {
            database.getUserScoreDao().getUserScore(uuid).toDomainModel()
        }
    }

    suspend fun getAllUserScores(): List<UserScore> {
        return withContext(Dispatchers.IO) {
            database.getUserScoreDao().getAllUserScores().toDomainModel()
        }
    }

    suspend fun insertUserScore(userScore: UserScore) {
        withContext(Dispatchers.IO) {
            database.getUserScoreDao().insert(userScore.toEntityModel())
        }
    }

}