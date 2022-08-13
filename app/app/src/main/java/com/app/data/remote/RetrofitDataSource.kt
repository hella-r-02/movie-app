package com.app.data.remote

import com.app.domain.model.Movie
import com.app.domain.model.MovieDetails

interface RetrofitDataSource {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): MovieDetails
}