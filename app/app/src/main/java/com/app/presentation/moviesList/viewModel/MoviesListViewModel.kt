package com.app.presentation.moviesList.viewModel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.data.MovieRepositoryImpl
import com.app.model.Movie
import kotlinx.coroutines.*

class MoviesListViewModel(
    private val repository: MovieRepositoryImpl
) : ViewModel() {
    private val _mutableLiveDataMovies = MutableLiveData<List<Movie>>(emptyList())
    val liveDataMovies get() = _mutableLiveDataMovies

    fun loadMovies() {
        viewModelScope.launch {
            val movies = repository.loadMovies()
            _mutableLiveDataMovies.value = movies
        }
    }
}
