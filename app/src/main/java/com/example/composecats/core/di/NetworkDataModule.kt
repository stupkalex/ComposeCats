package com.example.composecats.core.di

import android.app.Application
import android.widget.Toast
import com.example.composecats.core.network.retrofit.ApiFactory
import com.example.composecats.core.network.retrofit.ApiService
import com.example.composecats.core.network.retrofit.OnConnectionTimeoutListener
import com.stupkalex.rostok.di.ApplicationScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Module
interface NetworkDataModule {

    companion object {

        @ApplicationScope
        @Provides
        fun provideApiFactory(application: Application) : ApiFactory {
            return ApiFactory(object : OnConnectionTimeoutListener {
                override fun onConnectionTimeout() {
                    val scope = CoroutineScope(Dispatchers.Main)
                    scope.launch {
                        Toast.makeText(application, "SocketTimeoutException", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        @ApplicationScope
        @Provides
        fun provideApiService(apiFactory: ApiFactory) : ApiService {
            return apiFactory.apiService
        }
    }

}