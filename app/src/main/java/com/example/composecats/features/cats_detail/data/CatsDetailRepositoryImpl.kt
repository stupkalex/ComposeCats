package com.example.composecats.features.cats_detail.data

import com.example.composecats.core.database.CatsDao
import com.example.composecats.core.entity.CatEntity
import javax.inject.Inject

class CatsDetailRepositoryImpl @Inject constructor(
    private val database: CatsDao
) : CatsDetailRepository {
    override suspend fun getCatById(id: String): CatEntity {
        return database.getCatsById(id)
    }

}