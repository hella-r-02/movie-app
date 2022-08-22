package com.app.domain

import com.app.domain.model.Movie
import com.app.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMoviesFlow(): Flow<List<Movie>>
    suspend fun loadMovie(movieId: Int): MovieDetails?
    suspend fun loadMoviesFromApi(): List<Movie>
    suspend fun insertMoviesToDb(movies: List<Movie>)
    suspend fun updateIsLikeMovie(movie: Movie)
}