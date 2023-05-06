package com.example.composecats.core.local.entity

import android.os.Bundle
import androidx.navigation.NavType
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats_table")
data class CatEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "network_url")
    val networkUrl: String,
    @ColumnInfo(name = "local_url")
    val localUrl: String? = null,
    @ColumnInfo(name = "is_download")
    val isDownload: Boolean = false,
    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean = false,
    @ColumnInfo(name = "ratio")
    val ratio: Float,
    @ColumnInfo(name = "date")
    val date: Long
) {
    companion object {
        val NavigationType: NavType<String> = object : NavType<String>(false) {
            override fun get(bundle: Bundle, key: String): String? {
                return bundle.getString(key)
            }

            override fun parseValue(value: String): String {
                return value
            }

            override fun put(bundle: Bundle, key: String, value: String) {
                return bundle.putString(key, value)
            }

        }
    }
}