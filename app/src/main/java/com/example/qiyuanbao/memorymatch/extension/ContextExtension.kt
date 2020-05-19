package com.example.qiyuanbao.memorymatch.extension

import android.content.Context

fun Context.dpToPx(dp: Float): Float {
    return dp * resources.displayMetrics.density
}

fun Context.dpToPx(dp: Int): Int {
    return (dp.toFloat() * resources.displayMetrics.density).toInt()
}