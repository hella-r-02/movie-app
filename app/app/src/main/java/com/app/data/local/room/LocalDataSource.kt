package com.app.data.local.room

import com.app.domain.model.Movie
import com.app.domain.model.MovieDetails

interface LocalDataSource {
    suspend fun loadMovies(): List<Movie>

    fun insertMovies(movieFromNetwork: List<Movie>)

    suspend fun loadMovieDetails(id: Int): MovieDetails?

    suspend fun insertMovieDetails(movieFromNetwork: MovieDetails)
}