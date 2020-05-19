package com.example.qiyuanbao.memorymatch.model

import androidx.databinding.BaseObservable
import com.squareup.moshi.Json

data class ProductImage(
    @Json(name = "id") val imgId: String,
    @Json(name = "product_id") val productId: String,
    @Json(name = "src") val imgSrcUrl: String,
    var status: Status = Status.AWAIT
) : BaseObservable()
