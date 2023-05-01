package com.example.composecats.features.cats_detail.di

import com.example.composecats.features.cats_detail.data.CatsDetailRepository
import com.example.composecats.features.cats_detail.data.CatsDetailRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
@DetailScreenScope
interface DetailModule {

   @Binds
   @DetailScreenScope
   fun bindDetailRepository(impl: CatsDetailRepositoryImpl): CatsDetailRepository

}