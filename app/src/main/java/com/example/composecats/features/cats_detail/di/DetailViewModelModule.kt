package com.example.composecats.features.cats_detail.di

import androidx.lifecycle.ViewModel
import com.example.composecats.features.cats_detail.presentation.DetailViewModel
import com.stupkalex.rostok.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DetailViewModelModule {

    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    @DetailScreenScope
    @Binds
    fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel

}