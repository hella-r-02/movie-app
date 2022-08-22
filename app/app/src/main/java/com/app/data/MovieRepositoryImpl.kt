package com.app.data

import com.app.data.remote.RetrofitDataSource
import com.app.domain.MovieRepository
import com.app.domain.model.Movie
import com.app.domain.model.MovieDetails
import com.app.data.local.room.LocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.lang.Exception

class MovieRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val dataSource: RetrofitDataSource
) : MovieRepository {
    override suspend fun loadMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val movieDB = localDataSource.loadMovies()
        return@withContext movieDB.ifEmpty {
            try {
                val movieFromNetwork = dataSource.loadMovies()
                localDataSource.insertMovies(movieFromNetwork)
                movieFromNetwork
            } catch (ex: Exception) {
                emptyList()
            }
        }
    }

    override suspend fun loadMoviesFlow(): Flow<List<Movie>> = withContext(Dispatchers.IO) {
        return@withContext localDataSource.loadMoviesFlow()
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails? {
        val movieDb = localDataSource.loadMovieDetails(movieId)
        return if (movieDb == null) {
            try {
                val movieFromNetwork = dataSource.loadMovie(movieId)
                localDataSource.insertMovieDetails(movieFromNetwork)
                movieFromNetwork
            } catch (ex: Exception) {
                null
            }
        } else {
            movieDb
        }
    }

    override suspend fun loadMoviesFromApi(): List<Movie> = withContext(Dispatchers.IO) {
        return@withContext dataSource.loadMovies()
    }

    override suspend fun insertMoviesToDb(movies: List<Movie>) = withContext(Dispatchers.IO) {
        localDataSource.insertMovies(movies)
    }

    override suspend fun updateIsLikeMovie(movie: Movie) {
        movie.isLiked = !movie.isLiked
        localDataSource.updateLikeForMovie(movie)
    }
}