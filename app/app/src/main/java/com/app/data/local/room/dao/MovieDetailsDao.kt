package com.app.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.app.data.local.room.entity.relations.MovieDetailsWithGenresAndActors

@Dao
interface MovieDetailsDao {
//    @Query("SELECT * FROM movies_details")
//    fun getMovieWithGenresAndActors(id: Int): List<MovieDetailsWithGenresAndActors>
}