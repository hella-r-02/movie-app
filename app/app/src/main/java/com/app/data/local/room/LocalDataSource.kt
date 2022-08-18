package com.app.data.local.room

import com.app.domain.model.Movie
import com.app.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun loadMoviesFlow(): Flow<List<Movie>>

    fun insertMovies(movieFromNetwork: List<Movie>)

    suspend fun loadMovieDetails(id: Int): MovieDetails?

    suspend fun insertMovieDetails(movieFromNetwork: MovieDetails)
}