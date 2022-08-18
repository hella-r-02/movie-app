package com.app.presentation.moviesList.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.data.MovieRepositoryImpl
import com.app.domain.MovieRepository
import com.app.domain.model.Movie
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MoviesListViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    private val _mutableLiveDataMovies = MutableLiveData<List<Movie>>(emptyList())
    val liveDataMovies get() = _mutableLiveDataMovies

    fun loadMovies() {
        viewModelScope.launch {
            repository.loadMoviesFlow().collect { item -> _mutableLiveDataMovies.value = item }
        }
    }
}
