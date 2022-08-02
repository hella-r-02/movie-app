package com.app.dI

import com.app.model.Movie
import com.app.model.MovieDetails

interface RetrofitDataSource {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): MovieDetails
}