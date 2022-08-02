package com.app.presentation.moviesList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.data.MovieRepositoryImpl

class MoviesListViewModelFactory(private val movieRepository: MovieRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MoviesListViewModel(movieRepository) as T
}
