package com.example.composecats.features.feed_cats.domain.usecases

import com.example.composecats.core.entity.CatEntity
import com.example.composecats.features.feed_cats.data.FeedRepository
import javax.inject.Inject

class GetFavouriteCatsUseCase @Inject constructor(private val repository: FeedRepository) {

    suspend operator fun invoke(): List<CatEntity> {
        return repository.getFavouriteCats()
    }

}