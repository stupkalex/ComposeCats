package com.example.composecats.core.image_cache.domain

import android.graphics.drawable.Drawable
import com.example.composecats.core.local.entity.CatEntity

interface ImageRepository {

    fun saveImageToCache(drawable: Drawable, catEntity: CatEntity)

    fun saveImageToDownload(catEntity: CatEntity)

}