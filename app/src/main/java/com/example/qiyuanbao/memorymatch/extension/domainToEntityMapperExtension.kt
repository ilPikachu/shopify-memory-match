package com.example.qiyuanbao.memorymatch.extension

import com.example.qiyuanbao.memorymatch.database.entitity.ProductImageEntity
import com.example.qiyuanbao.memorymatch.database.entitity.UserScoreEntity
import com.example.qiyuanbao.memorymatch.model.ProductImage
import com.example.qiyuanbao.memorymatch.model.UserScore

// Mapper for mapping ProductImage domain Model to ProductImageEntity
@JvmName("productImagesToProductEntities")
fun List<ProductImage>.toEntityModel(): List<ProductImageEntity> {
    return map {
        ProductImageEntity(
            imgId = it.imgId,
            productId = it.productId,
            imgSrcUrl = it.imgSrcUrl
        )
    }
}

@JvmName("userScoreToUserScoreEntity")
fun UserScore.toEntityModel(): UserScoreEntity {
    return UserScoreEntity(
        scores = this.scores,
        uuid = this.uuid
    )
}