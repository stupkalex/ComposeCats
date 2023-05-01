package com.example.composecats.features.feed_cats.domain.usecases

import com.example.composecats.features.feed_cats.data.FeedRepository
import javax.inject.Inject

class ShowFavouriteCatsUseCase @Inject constructor(private val repository: FeedRepository) {

    suspend operator fun invoke() {
        return repository.showFavoriteCats()
    }

}
