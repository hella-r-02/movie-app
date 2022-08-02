package com.app.presentation.movieDetails

import com.app.model.MovieDetails

sealed class MovieState {
    class DefaultState(val movie: MovieDetails?) : MovieState()
    class ErrorState(val movie: MovieDetails?) : MovieState()
}