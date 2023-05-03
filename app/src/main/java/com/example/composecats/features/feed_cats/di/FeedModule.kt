package com.example.composecats.features.feed_cats.di

import com.example.composecats.core.di.ApplicationScope
import com.example.composecats.features.feed_cats.domain.FeedRepository
import com.example.composecats.features.feed_cats.data.FeedRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface FeedModule {

    @Binds
    @ApplicationScope
    fun bindFeedRepository(impl: FeedRepositoryImpl): FeedRepository

}