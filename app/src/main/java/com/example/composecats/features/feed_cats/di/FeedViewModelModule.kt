package com.example.composecats.features.feed_cats.di

import androidx.lifecycle.ViewModel
import com.example.composecats.features.feed_cats.presentation.FeedViewModel
import com.stupkalex.rostok.di.ApplicationScope
import com.stupkalex.rostok.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FeedViewModelModule {

    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    @ApplicationScope
    @Binds
    fun bindFeedViewModel(viewModel: FeedViewModel): ViewModel

}