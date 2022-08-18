package com.app.presentation.moviesList.viewModel

import android.util.Log
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
    val liveDataMovies get() = _mutableLiveDataMovies

    fun loadMoviesAsFlow() {
        viewModelScope.launch {
            Log.e("VIEW MODEL", "LOADED")
            repository.loadMoviesFlow().collect { item -> _mutableLiveDataMovies.value = item }
            Log.e("VIEW MODEL", "FINISHED")
        }
    }

    fun loadMovies() {
        viewModelScope.launch {
            _mutableLiveDataMovies.value = repository.loadMovies()
        }

    }
}
