package com.example.qiyuanbao.memorymatch.repository

import com.example.qiyuanbao.memorymatch.database.AppDatabase
import com.example.qiyuanbao.memorymatch.extension.toDomainModel
import com.example.qiyuanbao.memorymatch.extension.toEntityModel
import com.example.qiyuanbao.memorymatch.model.ProductImage
import com.example.qiyuanbao.memorymatch.network.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductImagesRepository(private val database: AppDatabase) {
    // TODO: Convert this to UserCases/Interactors
    /**
     * [com.example.qiyuanbao.memorymatch.database.dao.ProductImageDao]
     * getProductImages and getProductImages DAO methods are both marked as suspend,
     * therefore Room will ensure they are main safe.
     *
     * Note we could discard Dispatcher.IO because Room will use its own dispatcher to run
     * queries in the background thread, using Dispatcher.IO will actually make your queries slower.
     *
     * https://medium.com/androiddevelopers/coroutines-on-android-part-iii-real-work-2ba8a2ec2f45
     *
     * But I think it's clearer to developers that this is running on IO by withContext(Dispatchers.IO) explicitly
     */

    suspend fun getProductImages(): List<ProductImage> {
        return withContext(Dispatchers.IO) {
            database.getProductImageDao().getProductImages().toDomainModel()
        }
    }


    suspend fun refreshProductImages() {
        val productImages = ProductApi.retrofitService.getProducts().await()
            .products
            .map { product -> product.image }

        withContext(Dispatchers.IO) {
            database.getProductImageDao().insetAll(productImages.toEntityModel())
        }
    }
}