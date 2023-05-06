package com.example.composecats.core.image_cache.domain.usecases

import com.example.composecats.core.local.entity.CatEntity
import com.example.composecats.core.image_cache.domain.ImageRepository
import javax.inject.Inject

class SaveImageToDownloadsUseCase @Inject constructor(private val repository: ImageRepository) {

    operator fun invoke(catEntity: CatEntity) {
        return repository.saveImageToDownload(catEntity)
    }

}