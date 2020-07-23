package com.example.qiyuanbao.memorymatch.data.database.entitity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productImage")
data class ProductImageEntity (
    @PrimaryKey
    val imgId: String,
    val productId: String,
    val imgSrcUrl: String
)