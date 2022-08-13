package com.app.data.remote

import com.app.dI.API_KEY
import com.app.data.remote.service.ActorApiService
import com.app.data.remote.service.GenreApiService
import com.app.data.remote.service.MovieApiService
import com.app.domain.model.Actor
import com.app.domain.model.Genre
import com.app.domain.model.Movie
import com.app.domain.model.MovieDetails

private const val ADULT_AGE = 16
private const val CHILD_AGE = 13

class RetrofitDataSourceImpl(
    private val movieApiService: MovieApiService,
    private val genreApiService: GenreApiService,
    private val actorApiService: ActorApiService,
    private val imageUrlAppender: ImageUrlAppender
) : RetrofitDataSource {
    override suspend fun loadMovies(): List<Movie> {
        val genres = genreApiService.getGenres(API_KEY).genres
        return movieApiService.getUpComing(API_KEY).movies.map { movie ->
            Movie(
                id = movie.id,
                title = movie.title,
                imageUrl = imageUrlAppender.getMoviePosterImageUrl(movie.posterPath).toString(),
                rating = movie.voteAverage.toInt(),
                reviewCount = movie.voteCount,
                pgAge = getValidAge(movie.adult),
                runningTime = 100,
                isLiked = false,
                genres = genres.filter { genreResponse -> movie.genreIds.contains(genreResponse.id) }
                    .map { genre -> Genre(genre.id, genre.name) }
            )
        }
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {
        val movie = movieApiService.getMovieDetails(apiKey = API_KEY, movieId = movieId)
        return MovieDetails(
            id = movie.id,
            pgAge = getValidAge(movie.adult),
            title = movie.title,
            genres = movie.genres.map { genre -> Genre(genre.id, genre.name) },
            runningTime = 100,
            reviewCount = movie.revenue,
            isLiked = false,
            rating = movie.popularity.toInt(),
            imageUrl = imageUrlAppender.getDetailImageUrl(movie.backdropPath).toString(),
            storyLine = movie.overview.orEmpty(),
            actors = actorApiService.getActors(
                apiKey = API_KEY,
                movieId = movieId
            ).actors.map { actorResponse ->
                Actor(
                    id = actorResponse.id,
                    name = actorResponse.name,
                    imageUrl = imageUrlAppender.getActorImageUrl(actorResponse.profilePath)
                )
            }
        )
    }

    private fun getValidAge(adult: Boolean): Int {
        if (adult)
            return ADULT_AGE
        return CHILD_AGE
    }
}