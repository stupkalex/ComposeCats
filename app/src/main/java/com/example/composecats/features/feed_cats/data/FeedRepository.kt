package com.example.composecats.features.feed_cats.data

import com.example.composecats.core.entity.CatEntity

interface FeedRepository {

    suspend fun getCats(): List<CatEntity>

    suspend fun getFavouriteCats(): List<CatEntity>

}