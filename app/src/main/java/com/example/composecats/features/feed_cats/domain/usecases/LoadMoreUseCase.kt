package com.example.composecats.features.feed_cats.domain.usecases

import com.example.composecats.core.entity.CatEntity
import com.example.composecats.features.feed_cats.data.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadMoreUseCase @Inject constructor(private val repository: FeedRepository) {

    suspend operator fun invoke(): Flow<List<CatEntity>> {
        return repository.getFavouriteCats()
    }

}