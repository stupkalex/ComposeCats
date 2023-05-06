package com.example.composecats.core.network.dto

import android.util.Log
import com.example.composecats.core.local.entity.CatEntity
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.Calendar


data class CatDTO(
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Float,
    @SerializedName("height")
    val height: Float
)

fun CatDTO.toEntity() : CatEntity {
    val ratio = width / height
    val date = System.currentTimeMillis()
    Log.d("sdgdfg345", date.toString())
    return CatEntity(
        id = this.id,
        networkUrl = this.url,
        ratio = ratio,
        date = date
    )
}