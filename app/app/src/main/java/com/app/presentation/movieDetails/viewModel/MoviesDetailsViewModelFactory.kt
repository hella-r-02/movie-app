package com.app.presentation.movieDetails.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.domain.MovieRepository
import javax.inject.Inject

class MoviesDetailsViewModelFactory @Inject constructor( val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MoviesDetailsViewModel(movieRepository) as T
}
