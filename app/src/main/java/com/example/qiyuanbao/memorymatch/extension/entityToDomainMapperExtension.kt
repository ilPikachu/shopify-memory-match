package com.example.qiyuanbao.memorymatch.extension

import com.example.qiyuanbao.memorymatch.database.entitity.ProductImageEntity
import com.example.qiyuanbao.memorymatch.model.ProductImage

// Mapper for mapping ProductImageEntity to ProductImage domain Model
fun List<ProductImageEntity>.toDomainModel(): List<ProductImage> {
    return map {
        ProductImage(
            imgId = it.imgId,
            productId = it.productId,
            imgSrcUrl = it.imgSrcUrl
        )
    }
}