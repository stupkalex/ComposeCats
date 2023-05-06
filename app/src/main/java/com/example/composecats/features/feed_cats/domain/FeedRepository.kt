package com.example.composecats.features.feed_cats.domain

import com.example.composecats.core.Patch
import com.example.composecats.core.local.entity.CatEntity
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    suspend fun getCats(): Flow<out Patch>

    suspend fun showFavoriteCats()

    suspend fun showAllCats()

    suspend fun loadMore()

}