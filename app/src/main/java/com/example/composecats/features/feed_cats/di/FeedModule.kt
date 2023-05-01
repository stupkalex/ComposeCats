package com.example.composecats.features.feed_cats.di

import com.example.composecats.features.cats_detail.di.DetailScreenScope
import com.example.composecats.features.feed_cats.data.FeedRepository
import com.example.composecats.features.feed_cats.data.FeedRepositoryImpl
import com.stupkalex.rostok.di.ApplicationScope
import dagger.Binds
import dagger.Module

@Module
@ApplicationScope
interface FeedModule {

    @Binds
    @ApplicationScope
    fun bindFeedRepository(impl: FeedRepositoryImpl): FeedRepository

}