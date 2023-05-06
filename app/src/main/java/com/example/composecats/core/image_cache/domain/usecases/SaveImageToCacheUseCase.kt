package com.example.composecats.core.image_cache.domain.usecases

import android.graphics.drawable.Drawable
import com.example.composecats.core.local.entity.CatEntity
import com.example.composecats.core.image_cache.domain.ImageRepository
import javax.inject.Inject

class SaveImageToCacheUseCase @Inject constructor(private val repository: ImageRepository) {

    operator fun invoke(drawable: Drawable, catEntity: CatEntity) {
        return repository.saveImageToCache(drawable, catEntity)
    }

}