package com.app.data.local.room

import com.app.domain.model.Genre
import com.app.domain.model.Movie
import com.app.data.local.room.entity.GenreEntity
import com.app.data.local.room.entity.MovieEntity

class RoomDataSource(private val db: AppRoomDatabase) : LocalDataSource {

    override suspend fun loadMovies(): List<Movie> {
        val moviesWithGenres = db.getMovieDao().getMoviesWithGenres()
        val moviesList = mutableListOf<Movie>()
        for (movieDb in moviesWithGenres) {
            moviesList += Movie(
                id = movieDb.movieEntity.movieId,
                pgAge = movieDb.movieEntity.pgAge,
                title = movieDb.movieEntity.title,
                genres = movieDb.genres.map { genre ->
                    Genre(genre.genreId, genre.name)
                },
                runningTime = movieDb.movieEntity.runningTime,
                reviewCount = movieDb.movieEntity.reviewCount,
                isLiked = movieDb.movieEntity.isLiked,
                rating = movieDb.movieEntity.rating,
                imageUrl = movieDb.movieEntity.imageUrl
            )
        }
        return moviesList
    }

    override fun insertMovies(movieFromNetwork: List<Movie>) {
        val moviesList = mutableListOf<MovieEntity>()
        val genresList = mutableListOf<GenreEntity>()
        for (movieNet in movieFromNetwork) {
            moviesList += MovieEntity(
                movieId = movieNet.id,
                pgAge = movieNet.pgAge,
                title = movieNet.title,
                runningTime = movieNet.runningTime,
                reviewCount = movieNet.reviewCount,
                isLiked = movieNet.isLiked,
                rating = movieNet.rating,
                imageUrl = movieNet.imageUrl
            )
            for (genres in movieNet.genres) {
                genresList += GenreEntity(
                    genreId = genres.id,
                    name = genres.name,
                    movieId = movieNet.id
                )
            }
        }
        db.getMovieDao().insertMovies(moviesList)
        db.getGenreDao().insertGenres(genresList)
    }
}