package com.example.composecats.features.feed_cats.domain.usecases

import com.example.composecats.core.Patch
import com.example.composecats.features.feed_cats.domain.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(private val repository: FeedRepository) {

    suspend operator fun invoke(): Flow<Patch> {
        return repository.getCats()
    }

}