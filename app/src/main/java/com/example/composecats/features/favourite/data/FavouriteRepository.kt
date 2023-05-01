package com.example.composecats.features.favourite.data

interface FavouriteRepository {

    suspend fun addCatToFavourite(): Unit

    suspend fun deleteCatToFavourite(): Unit

}