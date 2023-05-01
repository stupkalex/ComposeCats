package com.example.composecats.features.cats_detail.domain.usercases

import com.example.composecats.core.entity.CatEntity
import com.example.composecats.core.network.NetworkResult
import com.example.composecats.features.cats_detail.data.CatsDetailRepository
import javax.inject.Inject

class GetCatByIdUseCase @Inject constructor(private val repository: CatsDetailRepository) {

    suspend operator fun invoke(id: String): NetworkResult<CatEntity> {
        return repository.getCatById(id)
    }

}