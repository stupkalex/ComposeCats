package com.example.composecats.features.cats_detail.di

import dagger.Subcomponent

@Subcomponent(modules = [DetailModule::class])
interface DetailComponent {

    /*fun inject(application: ComposeCatsApplication)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent

    }*/

}