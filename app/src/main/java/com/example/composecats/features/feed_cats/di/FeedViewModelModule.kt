package com.example.composecats.features.feed_cats.di

import androidx.lifecycle.ViewModel
import com.example.composecats.core.di.ViewModelKey
import com.example.composecats.features.feed_cats.presentation.FeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FeedViewModelModule {

    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    @Binds
    fun bindFeedViewModel(viewModel: FeedViewModel): ViewModel

}