package com.example.composecats.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats_table")
data class CatEntity(
    @PrimaryKey
    val id: String,
    val networkUrl: String,
    val localUrl: String? = null,
    val isDownload: Boolean = false,
    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean = false
)