package com.example.composecats.features.save_images.domain.usecases

import android.graphics.drawable.Drawable
import com.example.composecats.core.entity.CatEntity
import com.example.composecats.features.save_images.domain.ImageRepository
import javax.inject.Inject

class SaveImageToCacheUseCase @Inject constructor(private val repository: ImageRepository) {

    operator fun invoke(drawable: Drawable, catEntity: CatEntity) {
        return repository.saveImageToCache(drawable, catEntity)
    }

}