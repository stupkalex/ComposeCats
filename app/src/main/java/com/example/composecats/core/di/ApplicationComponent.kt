package com.stupkalex.rostok.di

import android.app.Application
import com.example.composecats.core.application.ComposeCatsApplication
import com.example.composecats.core.di.DataLocalModule
import com.example.composecats.core.di.NetworkDataModule
import com.example.composecats.features.cats_detail.di.DetailViewModelModule
import com.example.composecats.features.favourite.di.FavouriteModule
import com.example.composecats.features.feed_cats.di.FeedModule
import com.example.composecats.features.feed_cats.di.FeedViewModelModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [
    DataLocalModule::class,
    NetworkDataModule::class,
    FeedModule::class,
    FavouriteModule::class,
    DetailViewModelModule::class,
    FeedViewModelModule::class
])
interface ApplicationComponent {

    fun inject(application: ComposeCatsApplication)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent

    }

}