package com.example.composecats.core.di

import android.content.Context
import com.example.composecats.core.local.database.AppDatabase
import com.example.composecats.core.local.database.CatsDao
import com.example.composecats.core.image_cache.domain.ImageRepository
import com.example.composecats.core.image_cache.data.ImageRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
interface DataLocalModule {

    companion object{
        @ApplicationScope
        @Provides
        fun provideLocalDatabaseDao(context: Context): CatsDao {
            return AppDatabase.getInstance(context).catsDao()
        }

        @ApplicationScope
        @Provides
        fun provideImageRepository(context: Context, database: CatsDao): ImageRepository {
            return ImageRepositoryImpl(database, context)
        }

    }

}