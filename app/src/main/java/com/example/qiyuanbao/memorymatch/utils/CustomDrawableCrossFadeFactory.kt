package com.example.qiyuanbao.memorymatch.utils

import android.graphics.drawable.Drawable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.transition.*

/**
 * A Custom Drawable CrossFade Factory show transition even if data are load from Glide memory
 * cache, default [DrawableCrossFadeFactory] does not show transition if loaded from memory cache
 */

class CustomDrawableCrossFadeFactory private constructor(
    private val duration: Int,
    private val isCrossFadeEnabled: Boolean
) : TransitionFactory<Drawable> {
    private var resourceTransition: DrawableCrossFadeTransition? = null

    override fun build(dataSource: DataSource, isFirstResource: Boolean): Transition<Drawable?>? {
        return getResourceTransition()
    }

    private fun getResourceTransition(): Transition<Drawable?>? {
        if (resourceTransition == null) {
            resourceTransition = DrawableCrossFadeTransition(duration, isCrossFadeEnabled)
        }
        return resourceTransition
    }

    /**
     * A Builder for [CustomDrawableCrossFadeFactory].
     */
    class Builder constructor(private val durationMillis: Int = DEFAULT_DURATION_MS) {
        companion object {
            private const val DEFAULT_DURATION_MS = 200
        }

        private var isCrossFadeEnabled = false

        fun setCrossFadeEnabled(isCrossFadeEnabled: Boolean): Builder {
            this.isCrossFadeEnabled = isCrossFadeEnabled
            return this
        }

        fun build(): CustomDrawableCrossFadeFactory {
            return CustomDrawableCrossFadeFactory(durationMillis, isCrossFadeEnabled)
        }
    }
}