package com.example.qiyuanbao.memorymatch.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.qiyuanbao.memorymatch.database.entitity.ProductImageEntity

@Dao
interface ProductImageDao {
    @Query("select * from productImage")
    suspend fun getProductImages(): List<ProductImageEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insetAll(productImages: List<ProductImageEntity>)
}