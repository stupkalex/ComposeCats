package com.example.composecats.features.cats_detail.data

import com.example.composecats.core.entity.CatEntity
import com.example.composecats.core.network.NetworkResult

interface CatsDetailRepository {

    suspend fun getCatById(id: String) : CatEntity

}