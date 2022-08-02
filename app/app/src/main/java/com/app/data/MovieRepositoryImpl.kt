package com.app.data

import com.app.dI.RetrofitDataSource
import com.app.domain.MovieRepository
import com.app.domain.model.Movie
import com.app.domain.model.MovieDetails

class MovieRepositoryImpl(
    private val dataSource: RetrofitDataSource
) : MovieRepository {
    override suspend fun loadMovies(): List<Movie> {
        return dataSource.loadMovies()
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails? {
        return dataSource.loadMovie(movieId)
    }
}