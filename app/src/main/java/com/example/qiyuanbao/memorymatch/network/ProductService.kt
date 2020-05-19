package com.example.qiyuanbao.memorymatch.network

import com.example.qiyuanbao.memorymatch.model.ProductResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6
private const val PRODUCT_BASE_URL = "https://shopicruit.myshopify.com/admin/"
private const val PRODUCT_JSON = "products.json"
private const val PRODUCT_PAGE = "1"
private const val PRODUCT_ACCESS_TOKEN = "c32313df0d0ef512ca64d5b336a0d7c6"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(PRODUCT_BASE_URL)
    .build()

interface ProductApiService {
    @GET(PRODUCT_JSON)
    fun getProducts(@Query("page") pageNum: String = PRODUCT_PAGE,
                    @Query("access_token") accessToken: String = PRODUCT_ACCESS_TOKEN):
            Deferred<ProductResponse>
}

// Lazy init retrofit service
object ProductApi {
    val retrofitService: ProductApiService by lazy { retrofit.create(ProductApiService::class.java) }
}