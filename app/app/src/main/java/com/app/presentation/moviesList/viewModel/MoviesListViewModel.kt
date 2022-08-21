package com.app.presentation.moviesList.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.MovieRepository
import com.app.domain.model.Movie
import kotlinx.coroutines.*

class MoviesListViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    private val _mutableLiveDataMovies = MutableLiveData<List<Movie>>(emptyList())
    private val _mutableLiveDataIsError = MutableLiveData<Boolean>(false)
    val liveDataMovies get() = _mutableLiveDataMovies
    val liveDataIsError get() = _mutableLiveDataIsError

    fun loadMoviesAsFlow() {
        viewModelScope.launch {
            repository.loadMoviesFlow().collect { item -> _mutableLiveDataMovies.value = item }
        }
    }

    fun loadMovies() {
        viewModelScope.launch {
            val movies = repository.loadMovies()
            if (movies.isEmpty()) {
                _mutableLiveDataIsError.value = true
            } else {
                _mutableLiveDataIsError.value = false
                _mutableLiveDataMovies.value = repository.loadMovies()
            }
        }

    }
}
