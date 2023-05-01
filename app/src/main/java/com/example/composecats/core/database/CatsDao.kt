package com.example.composecats.core.database

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.composecats.core.entity.CatEntity

@Dao
interface CatsDao {

    @Query("SELECT * FROM cats_table")
    abstract fun getAllCats() : List<CatEntity>

    @Query("SELECT * FROM cats_table WHERE is_favourite = 1")
    abstract fun getAllFavouriteCats() : List<CatEntity>

    @Query("SELECT * FROM cats_table WHERE id = :id LIMIT 1")
    abstract fun getCatsById(id: String) : CatEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateCat(cat: CatEntity)
}