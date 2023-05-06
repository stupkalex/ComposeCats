package com.example.composecats.features.favourite.domain.usecases

import com.example.composecats.core.local.entity.CatEntity
import com.example.composecats.features.favourite.domain.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCatStateUseCase @Inject constructor(private val repository: FavouriteRepository) {

    suspend operator fun invoke(): Flow<CatEntity> {
        return repository.updateCatState()
    }

}