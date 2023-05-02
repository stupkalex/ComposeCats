package com.example.composecats.core.network.dto

import com.example.composecats.core.entity.CatEntity
import com.google.gson.annotations.SerializedName


data class CatDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String
)

fun CatDTO.toEntity() = CatEntity(
    id = this.id, networkUrl = this.url
)