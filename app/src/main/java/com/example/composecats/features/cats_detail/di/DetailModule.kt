package com.example.composecats.features.cats_detail.di

import com.example.composecats.features.cats_detail.domain.CatsDetailRepository
import com.example.composecats.features.cats_detail.data.CatsDetailRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface DetailModule {

   @Binds
   fun bindDetailRepository(impl: CatsDetailRepositoryImpl): CatsDetailRepository

}