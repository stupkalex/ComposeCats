package com.example.composecats.core

import com.example.composecats.core.entity.CatEntity

data class Patch(
    val type: PatchType,
    val content: List<CatEntity>,
    var nexDataIsLoading: Boolean = false
)

enum class PatchType {
    UpdateDataSet, LoadMore, ErrorRequest
}
