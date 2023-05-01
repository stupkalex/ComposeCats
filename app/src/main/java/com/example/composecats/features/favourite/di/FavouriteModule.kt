package com.example.composecats.features.favourite.di

import com.example.composecats.features.favourite.data.FavouriteRepository
import com.example.composecats.features.favourite.data.FavouriteRepositoryImpl
import com.stupkalex.rostok.di.ApplicationScope
import dagger.Binds
import dagger.Module

@Module
@ApplicationScope
interface FavouriteModule {

    @Binds
    fun bindFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository
}