package com.app.moviesDetails.viewModel

import com.app.model.Movie

sealed class MovieState {
    class DefaultState(val movie: Movie?) : MovieState()
    class ErrorState(val movie: Movie?) : MovieState()
}