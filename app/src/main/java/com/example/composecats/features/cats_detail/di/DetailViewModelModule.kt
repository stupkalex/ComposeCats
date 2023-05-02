package com.example.composecats.features.cats_detail.di

import androidx.lifecycle.ViewModel
import com.example.composecats.core.di.ViewModelKey
import com.example.composecats.features.cats_detail.presentation.DetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface DetailViewModelModule {

    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    @Binds
    fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel

}