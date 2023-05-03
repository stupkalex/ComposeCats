package com.example.composecats.core.database

import androidx.room.*
import com.example.composecats.core.entity.CatEntity

@Dao
interface CatsDao {

    @Query("SELECT * FROM cats_table ORDER BY date")
    suspend fun getAllCats() : List<CatEntity>

    @Query("SELECT * FROM cats_table WHERE is_favourite = 1 ORDER BY date")
    suspend fun getAllFavouriteCats() : List<CatEntity>

    @Query("SELECT * FROM cats_table WHERE id = :id LIMIT 1")
    suspend fun getCatsById(id: String) : CatEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCat(cat: CatEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCats(cats: List<CatEntity>)
}