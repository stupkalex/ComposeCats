package com.example.composecats.core.network.retrofit

import com.example.composecats.core.network.dto.CatDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("images/search/")
    suspend fun getCatsBatch(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Flow<Response<List<CatDTO>>>

}