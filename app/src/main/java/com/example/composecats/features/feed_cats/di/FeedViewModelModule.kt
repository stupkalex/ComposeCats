package com.example.composecats.features.feed_cats.di

import androidx.lifecycle.ViewModel
import com.example.composecats.features.cats_detail.presentation.DetailViewModel
import com.example.composecats.features.feed_cats.presentation.FeedViewModel
import com.stupkalex.rostok.di.ApplicationScope
import com.stupkalex.rostok.di.ViewModelKey
import dagger.Module
import dagger.multibindings.IntoMap

@Module
@ApplicationScope
interface FeedViewModelModule {

    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    fun bindFeedViewModel(viewModel: FeedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel

}