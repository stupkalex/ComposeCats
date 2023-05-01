package com.example.composecats.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatDTO(
    @SerialName("id")
    val id: String,
    @SerialName("url")
    val url: String
)