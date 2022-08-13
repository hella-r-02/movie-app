package com.app.data.local.room

import com.app.data.local.room.entity.ActorEntity
import com.app.domain.model.Genre
import com.app.domain.model.Movie
import com.app.data.local.room.entity.GenreEntity
import com.app.data.local.room.entity.MovieDetailsEntity
import com.app.data.local.room.entity.MovieEntity
import com.app.data.local.room.entity.relations.MovieDetailsWithGenresAndActors
import com.app.domain.model.Actor
import com.app.domain.model.MovieDetails

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
                    movieId = movieNet.id,
                    movieDetailsId = null
                )
            }
        }
        db.getMovieDao().insertMovies(moviesList)
        db.getGenreDao().insertGenres(genresList)
    }

    override suspend fun loadMovieDetails(id: Int): MovieDetails? {
        val movieWithGenreAndActor: MovieDetailsWithGenresAndActors? =
            db.getMovieDetailsDao().getMovieWithGenresAndActors(id)
        if (movieWithGenreAndActor != null) {
            val movie: MovieDetailsEntity = movieWithGenreAndActor.movieDetailsEntity
            return MovieDetails(
                id = movie.movieDetailsId,
                pgAge = movie.pgAge,
                title = movie.title,
                genres = movieWithGenreAndActor.genres.map { genre ->
                    Genre(genre.genreId, genre.name)
                },
                runningTime = movie.runningTime,
                reviewCount = movie.reviewCount,
                isLiked = movie.isLiked,
                rating = movie.rating,
                imageUrl = movie.imageUrl,
                storyLine = movie.storyLine,
                actors = movieWithGenreAndActor.actors.map { actor ->
                    Actor(id = actor.actorId, name = actor.name, imageUrl = actor.imageUrl)
                }
            )
        }
        return null
    }

    override suspend fun insertMovieDetails(movieFromNetwork: MovieDetails) {
        val genresList = mutableListOf<GenreEntity>()
        val actorsList = mutableListOf<ActorEntity>()
        val movieEntity = MovieDetailsEntity(
            movieDetailsId = movieFromNetwork.id,
            pgAge = movieFromNetwork.pgAge,
            title = movieFromNetwork.title,
            runningTime = movieFromNetwork.runningTime,
            reviewCount = movieFromNetwork.reviewCount,
            isLiked = movieFromNetwork.isLiked,
            rating = movieFromNetwork.rating,
            imageUrl = movieFromNetwork.imageUrl,
            storyLine = movieFromNetwork.storyLine
        )
        for (genre in movieFromNetwork.genres) {
            genresList += GenreEntity(
                genreId = genre.id,
                name = genre.name,
                movieId = movieFromNetwork.id,
                movieDetailsId = movieFromNetwork.id
            )
        }
        for (actor in movieFromNetwork.actors) {
            actorsList += ActorEntity(
                actorId = actor.id,
                name = actor.name,
                movieDetailsId = movieFromNetwork.id,
                imageUrl = actor.imageUrl
            )
        }
        db.getMovieDetailsDao().insert(movieEntity)
        db.getGenreDao().update(genresList)
        db.getActorDao().insertActors(actorsList)
    }
}