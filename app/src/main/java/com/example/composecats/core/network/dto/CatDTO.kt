package com.example.composecats.core.network.dto

import com.example.composecats.core.entity.CatEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatDTO(
    @SerialName("id")
    val id: String,
    @SerialName("url")
    val url: String
)

fun CatDTO.toEntity() = CatEntity(
    id = this.id, networkUrl = this.url
)