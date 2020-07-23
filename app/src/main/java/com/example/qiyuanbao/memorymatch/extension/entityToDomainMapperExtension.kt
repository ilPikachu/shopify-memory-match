package com.example.qiyuanbao.memorymatch.extension

import com.example.qiyuanbao.memorymatch.data.database.entitity.ProductImageEntity
import com.example.qiyuanbao.memorymatch.data.database.entitity.UserScoreEntity
import com.example.qiyuanbao.memorymatch.data.model.ProductImage
import com.example.qiyuanbao.memorymatch.data.model.UserScore

// Mapper for mapping ProductImageEntity to ProductImage domain Model
@JvmName("productEntitiesToProductImages")
fun List<ProductImageEntity>.toDomainModel(): List<ProductImage> {
    return map {
        ProductImage(
            imgId = it.imgId,
            productId = it.productId,
            imgSrcUrl = it.imgSrcUrl
        )
    }
}

@JvmName("UserScoreEntitiesToUserScores")
fun List<UserScoreEntity>.toDomainModel(): List<UserScore> {
    return map {
        UserScore(
            scores = it.scores,
            uuid = it.uuid
        )
    }
}

@JvmName("UserScoreEntityToUserScore")
fun UserScoreEntity.toDomainModel(): UserScore {
    return UserScore(
        scores = this.scores,
        uuid = this.uuid
    )
}