package com.example.composecats.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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

    val contentType = "application/json".toMediaType()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory(contentType))
        .client(okHTTP)
        .baseUrl(BASE_URL)
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}