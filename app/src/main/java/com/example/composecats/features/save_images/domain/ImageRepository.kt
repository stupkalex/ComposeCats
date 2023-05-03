package com.example.composecats.features.save_images.domain

import android.graphics.drawable.Drawable
import com.example.composecats.core.entity.CatEntity

interface ImageRepository {

    fun saveImageToCache(drawable: Drawable, catEntity: CatEntity)

    fun saveImageToDownload(catEntity: CatEntity)

}