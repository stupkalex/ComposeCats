package com.example.composecats.features.favourite.data

import com.example.composecats.core.database.CatsDao
import com.example.composecats.core.entity.CatEntity
import com.example.composecats.features.favourite.domain.FavouriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

private const val TAG = "FavouriteRepository"

class FavouriteRepositoryImpl @Inject constructor(
    private val database: CatsDao
) : FavouriteRepository {

    private val updateCatState = MutableSharedFlow<CatEntity>(replay = 1)

    override suspend fun addCatToFavourite(cat: CatEntity) {
        val updateCat = cat.copy(isFavourite = true)
        database.updateCat(updateCat)
        updateCatState.emit(updateCat)
    }

    override suspend fun deleteCatToFavourite(cat: CatEntity) {
        val updateCat = cat.copy(isFavourite = false)
        database.updateCat(updateCat)
        updateCatState.emit(updateCat)
    }

    override suspend fun updateCatState(): Flow<CatEntity> {
        return updateCatState
    }
}