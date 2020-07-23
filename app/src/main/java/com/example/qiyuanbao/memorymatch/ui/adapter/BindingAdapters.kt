package com.example.qiyuanbao.memorymatch.ui.adapter

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.qiyuanbao.memorymatch.R
import com.example.qiyuanbao.memorymatch.data.model.ProductImage
import com.example.qiyuanbao.memorymatch.data.model.Status

private const val WIDTH = 500
private const val HEIGHT = 500

// Called when the productImage is used for a ImageView.
@BindingAdapter("image")
fun bindImage(imageView: ImageView, productImage: ProductImage?) {
    productImage?.let {
        when (it.status) {
            Status.MATCH -> {
                val uri = it.imgSrcUrl.toUri().buildUpon().scheme("https").build()
                Glide.with(imageView.context)
                    .load(uri)
                    .override(WIDTH, HEIGHT)
                    .into(imageView)
            }
            Status.AWAIT -> {
                Glide.with(imageView.context)
                    .load(R.drawable.ic_question)
                    .override(WIDTH, HEIGHT)
                    .into(imageView)
            }
            Status.FLIP -> {
                val uri = it.imgSrcUrl.toUri().buildUpon().scheme("https").build()
                Glide.with(imageView.context)
                    .load(uri)
                    .override(WIDTH, HEIGHT)
                    .into(imageView)
            }
        }
    }
}
