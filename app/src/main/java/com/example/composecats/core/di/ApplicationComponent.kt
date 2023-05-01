package com.stupkalex.rostok.di

import android.app.Application
import com.example.composecats.core.application.ComposeCatsApplication
import com.example.composecats.core.di.DataLocalModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataLocalModule::class])
interface ApplicationComponent {

    fun inject(application: ComposeCatsApplication)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent

    }

}