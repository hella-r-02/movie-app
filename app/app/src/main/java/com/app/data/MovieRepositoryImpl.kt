package com.app.data

import com.app.data.remote.RetrofitDataSource
import com.app.domain.MovieRepository
import com.app.domain.model.Movie
import com.app.domain.model.MovieDetails
import com.app.data.local.room.LocalDataSource

class MovieRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val dataSource: RetrofitDataSource
) : MovieRepository {
    override suspend fun loadMovies(): List<Movie> {
        val movieDB = localDataSource.loadMovies()
        return if (movieDB.isEmpty()) {
            val movieFromNetwork = dataSource.loadMovies()
            localDataSource.insertMovies(movieFromNetwork)
            movieFromNetwork
        } else {
            movieDB
        }

    }

    override suspend fun loadMovie(movieId: Int): MovieDetails? {
        return dataSource.loadMovie(movieId)
    }
}