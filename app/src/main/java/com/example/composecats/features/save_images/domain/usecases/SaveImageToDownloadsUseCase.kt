package com.example.composecats.features.save_images.domain.usecases

import com.example.composecats.core.entity.CatEntity
import com.example.composecats.features.save_images.domain.ImageRepository
import javax.inject.Inject

class SaveImageToDownloadsUseCase @Inject constructor(private val repository: ImageRepository) {

    operator fun invoke(catEntity: CatEntity) {
        return repository.saveImageToDownload(catEntity)
    }

}