package com.app.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.data.local.room.entity.MovieDetailsEntity
import com.app.data.local.room.entity.relations.MovieDetailsWithGenresAndActors

@Dao
interface MovieDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieDetailsEntity: MovieDetailsEntity)

    @Query("SELECT * FROM movies_details WHERE movies_details.id = :id")
    fun getMovieWithGenresAndActors(id: Int): MovieDetailsWithGenresAndActors
}