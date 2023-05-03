package com.example.composecats.features.favourite.di

import com.example.composecats.core.di.ApplicationScope
import com.example.composecats.features.favourite.domain.FavouriteRepository
import com.example.composecats.features.favourite.data.FavouriteRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface FavouriteModule {

    @ApplicationScope
    @Binds
    fun bindFavouriteRepository(impl: FavouriteRepositoryImpl): FavouriteRepository
}