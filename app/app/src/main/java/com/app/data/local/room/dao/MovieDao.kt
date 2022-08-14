package com.app.data.local.room.dao

import androidx.room.*
import com.app.data.local.room.entity.MovieEntity
import com.app.data.local.room.entity.relations.MovieWithGenres

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(items: Iterable<MovieEntity>)

    @Query(
        "SELECT * FROM movies " +
                "JOIN genres ON genres.movieId = movies.id"
    )
    fun getMoviesWithGenres(): List<MovieWithGenres>

    @Query("Select * from movies")
    fun getAll(): List<MovieEntity>
}