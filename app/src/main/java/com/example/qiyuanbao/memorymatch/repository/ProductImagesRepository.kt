package com.example.qiyuanbao.memorymatch.repository

import com.example.qiyuanbao.memorymatch.database.AppDatabase
import com.example.qiyuanbao.memorymatch.extension.toDomainModel
import com.example.qiyuanbao.memorymatch.extension.toEntityModel
import com.example.qiyuanbao.memorymatch.model.ProductImage
import com.example.qiyuanbao.memorymatch.network.ProductApi

class ProductImagesRepository(private val database: AppDatabase) {
    /**
     * [com.example.qiyuanbao.memorymatch.database.dao.ProductImageDao]
     * getProductImages and getProductImages DAO methods are both marked as suspend,
     * therefore Room will ensure they are main safe.
     *
     * Note we don't use Dispatcher.IO because Room will use its own dispatcher to run
     * queries in the background thread, using Dispatcher.IO will actually make your queries slower.
     */

    suspend fun getProductImages(): List<ProductImage> {
        return database.getProductImageDao().getProductImages().toDomainModel()
    }


    suspend fun refreshProductImages() {
        val productImages = ProductApi.retrofitService.getProducts().await()
            .products
            .map { product -> product.image }

        database.getProductImageDao().insetAll(productImages.toEntityModel())
    }
}