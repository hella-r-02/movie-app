package com.app.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.data.local.room.entity.GenreEntity

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(genreEntity: GenreEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(items: Iterable<GenreEntity>)

    @Query("SELECT * FROM genres where id= :id")
    suspend fun getById(id: Long): GenreEntity
}