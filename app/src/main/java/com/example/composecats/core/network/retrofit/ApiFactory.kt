package com.example.composecats.core.network.retrofit

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.thdev.network.flowcalladapterfactory.FlowCallAdapterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiFactory @Inject constructor (listener: OnConnectionTimeoutListener) {

    private val BASE_URL = "https://api.thecatapi.com/v1/"

    @Throws(IOException::class)
    private fun onOnIntercept(chain: Interceptor.Chain, listener: OnConnectionTimeoutListener?): Response {
        val request: Request = chain.request()
        return try {
            chain.proceed(request)
        } catch (exception: SocketTimeoutException) {
            exception.printStackTrace()
            listener?.onConnectionTimeout()
            chain.proceed(request)
        }
    }

    private val interceptor = Interceptor { chain -> onOnIntercept(chain, listener) }

    private val log = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val okHTTP = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(log)
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(FlowCallAdapterFactory())
        .client(okHTTP)
        .baseUrl(BASE_URL)
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}