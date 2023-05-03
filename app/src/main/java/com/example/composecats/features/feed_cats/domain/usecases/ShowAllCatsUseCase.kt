package com.example.composecats.features.feed_cats.domain.usecases

import com.example.composecats.features.feed_cats.domain.FeedRepository
import javax.inject.Inject

class ShowAllCatsUseCase @Inject constructor(private val repository: FeedRepository) {

    suspend operator fun invoke() {
        return repository.showAllCats()
    }

}