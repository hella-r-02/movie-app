package com.app.data.local.room

import android.util.Log
import com.app.data.local.room.entity.ActorEntity
import com.app.domain.model.Genre
import com.app.domain.model.Movie
import com.app.data.local.room.entity.GenreEntity
import com.app.data.local.room.entity.MovieDetailsEntity
import com.app.data.local.room.entity.MovieEntity
import com.app.data.local.room.entity.relations.MovieDetailsWithGenresAndActors
import com.app.domain.model.Actor
import com.app.domain.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomDataSource(private val db: AppRoomDatabase) : LocalDataSource {

    override fun loadMoviesFlow(): Flow<List<Movie>> {
        Log.d("MovieApp", "MoviesRepository: loadMoviesFlow")
        return db.getMovieDao().getAllFlow().map { convertMovieEntityToMovie(it) }
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
        val movieEntity = toMovieDetailsEntity(movieFromNetwork)
        for (genre in movieFromNetwork.genres) {
            genresList += toGenreEntity(genre, movieFromNetwork.id)
        }
        for (actor in movieFromNetwork.actors) {
            actorsList += toActorEntity(actor, movieFromNetwork.id)
        }
        db.getMovieDetailsDao().insert(movieEntity)
        db.getGenreDao().update(genresList)
        db.getActorDao().insertActors(actorsList)
    }

    private fun toMovie(entity: MovieEntity): Movie = Movie(
        id = entity.movieId,
        title = entity.title,
        pgAge = entity.pgAge,
        genres = emptyList<Genre>(),
        runningTime = entity.runningTime,
        reviewCount = entity.reviewCount,
        isLiked = entity.isLiked,
        rating = entity.rating,
        imageUrl = entity.imageUrl
    )

    private fun toMovieDetailsEntity(entity: MovieDetailsEntity): MovieDetails {
        return MovieDetails(
            id = entity.movieDetailsId,
            title = entity.title,
            pgAge = entity.pgAge,
            genres = emptyList<Genre>(),
            runningTime = entity.runningTime,
            reviewCount = entity.reviewCount,
            isLiked = entity.isLiked,
            rating = entity.rating,
            imageUrl = entity.imageUrl,
            actors = emptyList(),
            storyLine = entity.storyLine,
        )
    }

    private fun toGenre(entity: GenreEntity): Genre = Genre(
        id = entity.genreId,
        name = entity.name
    )

    private fun toMovieDetailsEntity(movieDetails: MovieDetails): MovieDetailsEntity =
        MovieDetailsEntity(
            movieDetailsId = movieDetails.id,
            pgAge = movieDetails.pgAge,
            title = movieDetails.title,
            runningTime = movieDetails.runningTime,
            reviewCount = movieDetails.reviewCount,
            isLiked = movieDetails.isLiked,
            rating = movieDetails.rating,
            imageUrl = movieDetails.imageUrl,
            storyLine = movieDetails.storyLine
        )

    private fun toGenreEntity(genre: Genre, movieId: Int): GenreEntity = GenreEntity(
        genreId = genre.id,
        name = genre.name,
        movieId = movieId,
        movieDetailsId = movieId
    )

    private fun toActorEntity(actor: Actor, movieId: Int): ActorEntity = ActorEntity(
        actorId = actor.id,
        name = actor.name,
        movieDetailsId = movieId,
        imageUrl = actor.imageUrl
    )

    private suspend fun convertMovieEntityToMovie(moviesEntities: List<MovieEntity>): List<Movie> =
        withContext(Dispatchers.IO) {
            val movies: List<Movie> = moviesEntities.map { toMovie(it) }
            movies.forEach { movie ->
                movie.genres =
                    db.getGenreDao().getByMovieId(movie.id.toLong()).map { toGenre(it) }
            }
            return@withContext movies
        }
}