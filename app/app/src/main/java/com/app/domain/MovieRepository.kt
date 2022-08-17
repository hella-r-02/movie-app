package com.app.domain

import com.app.domain.model.Movie
import com.app.domain.model.MovieDetails

interface MovieRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): MovieDetails?
    suspend fun loadMoviesFromApi(): List<Movie>
    suspend fun insertMovieToDb(movies: List<Movie>)
}