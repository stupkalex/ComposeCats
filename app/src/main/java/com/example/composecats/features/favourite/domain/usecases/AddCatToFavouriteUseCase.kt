package com.example.composecats.features.favourite.domain.usecases

import com.example.composecats.core.entity.CatEntity
import com.example.composecats.features.favourite.data.FavouriteRepository
import javax.inject.Inject

class AddCatToFavouriteUseCase @Inject constructor(private val repository: FavouriteRepository) {

    suspend operator fun invoke(cat: CatEntity) {
        return repository.addCatToFavourite(cat)
    }

}