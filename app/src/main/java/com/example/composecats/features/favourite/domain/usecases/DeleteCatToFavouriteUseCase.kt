package com.example.composecats.features.favourite.domain.usecases

import com.example.composecats.features.favourite.data.FavouriteRepository
import javax.inject.Inject

class DeleteCatToFavouriteUseCase @Inject constructor(private val repository: FavouriteRepository) {

    suspend operator fun invoke(): Unit {
        return repository.deleteCatToFavourite()
    }

}