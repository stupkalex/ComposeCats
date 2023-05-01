package com.example.composecats.core.di

import android.app.Application
import com.example.composecats.core.database.AppDatabase
import com.example.composecats.core.database.CatsDao
import com.stupkalex.rostok.di.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
interface DataLocalModule {

    companion object{
        @ApplicationScope
        @Provides
        fun provideLocalDatabaseDao(context: Application): CatsDao {
            return AppDatabase.getInstance(context).catsDao()
        }
    }

}