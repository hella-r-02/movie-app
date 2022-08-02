package com.app.presentation.movieDetails.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.domain.MovieRepository

class MoviesDetailsViewModelFactory(private val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MoviesDetailsViewModel(movieRepository) as T
}
