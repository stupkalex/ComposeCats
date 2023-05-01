package com.example.composecats.core

import com.example.composecats.core.entity.CatEntity

data class Patch(val type: PatchType, val content: List<CatEntity>)

enum class PatchType {
    UpdateDataSet, NotifyItemChanged, LoadMore, ErrorRequest
}
