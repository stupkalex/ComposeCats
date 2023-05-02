package com.example.composecats.core.di

import android.app.Application
import android.content.Context
import com.example.composecats.core.database.AppDatabase
import com.example.composecats.core.database.CatsDao
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
    }

}