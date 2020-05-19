package com.example.qiyuanbao.memorymatch.extension

import com.example.qiyuanbao.memorymatch.database.entitity.ProductImageEntity
import com.example.qiyuanbao.memorymatch.model.ProductImage

// Mapper for mapping ProductImage domain Model to ProductImageEntity
fun List<ProductImage>.toEntityModel(): List<ProductImageEntity> {
    return map {
        ProductImageEntity(
            imgId = it.imgId,
            productId = it.productId,
            imgSrcUrl = it.imgSrcUrl
        )
    }
}