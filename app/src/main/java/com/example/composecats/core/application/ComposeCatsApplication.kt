package com.example.composecats.core.application

import android.app.Application
import com.stupkalex.rostok.di.DaggerApplicationComponent

class ComposeCatsApplication: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

}