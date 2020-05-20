package com.example.qiyuanbao.memorymatch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.qiyuanbao.memorymatch.database.dao.ProductImageDao
import com.example.qiyuanbao.memorymatch.database.dao.UserScoreDao
import com.example.qiyuanbao.memorymatch.database.entitity.ProductImageEntity
import com.example.qiyuanbao.memorymatch.database.entitity.UserScoreEntity

@Database(
    entities = [
        ProductImageEntity::class,
        UserScoreEntity::class
    ], version = 2
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "gamedb"
    }

    abstract fun getProductImageDao(): ProductImageDao

    abstract fun getUserScoreDao(): UserScoreDao
}

// DB singleton instance
private lateinit var INSTANCE: AppDatabase

fun getDateBase(context: Context): AppDatabase {
    synchronized(AppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            ).build()
        }
    }
    return INSTANCE
}