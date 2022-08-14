package com.app.data.local.room.dao

import androidx.room.*
import com.app.data.local.room.entity.GenreEntity

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(genreEntity: GenreEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(items: Iterable<GenreEntity>)

    @Query("SELECT * FROM genres where id= :id")
    suspend fun getById(id: Long): GenreEntity

    @Update
    fun update(genreEntity: GenreEntity)

    @Update
    fun update(genres: List<GenreEntity>): Int

    @Query("SELECT * FROM genres where movieId=:movieId")
    suspend fun getByMovieId(movieId: Long): List<GenreEntity>
}