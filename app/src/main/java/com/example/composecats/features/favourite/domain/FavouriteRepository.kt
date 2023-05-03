package com.example.composecats.features.favourite.domain

import com.example.composecats.core.entity.CatEntity
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    suspend fun addCatToFavourite(cat: CatEntity)

    suspend fun deleteCatToFavourite(cat: CatEntity)

    suspend fun updateCatState(): Flow<CatEntity>

}