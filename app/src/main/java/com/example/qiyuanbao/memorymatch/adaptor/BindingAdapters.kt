package com.example.qiyuanbao.memorymatch.adaptor

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.qiyuanbao.memorymatch.R
import com.example.qiyuanbao.memorymatch.model.ProductImage
import com.example.qiyuanbao.memorymatch.model.Status
import com.example.qiyuanbao.memorymatch.utils.CustomDrawableCrossFadeFactory

private const val WIDTH = 500
private const val HEIGHT = 500

// Called when the productImage is used for a ImageView.
@BindingAdapter("image")
fun bindImage(imageView: ImageView, productImage: ProductImage?) {
    val factory = CustomDrawableCrossFadeFactory.Builder()
        .setCrossFadeEnabled(true)
        .build()

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
                    .transition(DrawableTransitionOptions.with(factory))
                    .into(imageView)
            }
        }
    }
}

// Called when listData is used by a recyclerView, and when the ProductImage list changes.
@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<ProductImage>?
) {
    val adapter = recyclerView.adapter as GameGridAdapter
    data?.let {
        adapter.submitNewList(it)
    }
}