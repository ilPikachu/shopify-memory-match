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
