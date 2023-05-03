package com.example.composecats.features.cats_detail.domain.usercases

import com.example.composecats.core.entity.CatEntity
import com.example.composecats.features.cats_detail.domain.CatsDetailRepository
import javax.inject.Inject

class GetCatByIdUseCase @Inject constructor(private val repository: CatsDetailRepository) {

    suspend operator fun invoke(id: String): CatEntity {
        return repository.getCatById(id)
    }

}