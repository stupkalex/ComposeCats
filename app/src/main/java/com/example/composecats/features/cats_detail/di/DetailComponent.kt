package com.example.composecats.features.cats_detail.di

import com.example.composecats.core.di.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [DetailModule::class, DetailViewModelModule::class])
interface DetailComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance catId: String
        ): DetailComponent
    }
}