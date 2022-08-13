package com.app.presentation.moviesList.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.data.MovieRepositoryImpl
import com.app.domain.MovieRepository
import javax.inject.Inject

class MoviesListViewModelFactory @Inject constructor(val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MoviesListViewModel(movieRepository) as T
}
