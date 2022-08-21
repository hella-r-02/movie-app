package com.app.data

import android.util.Log
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
        return@withContext if (movieDB.isEmpty()) {
            Log.e("MovieRepository", "loadMoviesFromNetwork")
            try {
                val movieFromNetwork = dataSource.loadMovies()
                localDataSource.insertMovies(movieFromNetwork)
                movieFromNetwork
            } catch (ex: Exception) {
                emptyList()
            }
        } else {
            Log.e("MovieRepository", "loadMoviesFromDb")
            movieDB
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
        Log.e("MovieRep", "loadMoviesFromApi()")
        return@withContext dataSource.loadMovies()
    }

    override suspend fun insertMoviesToDb(movies: List<Movie>) = withContext(Dispatchers.IO) {
        Log.e("MovieRep", "insertMoviesToDb")
        localDataSource.insertMovies(movies)
    }
}