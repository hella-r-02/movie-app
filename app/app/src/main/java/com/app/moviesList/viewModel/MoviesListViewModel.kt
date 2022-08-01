package com.app.moviesList.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.data.MovieRepository
import com.app.model.Movie
import kotlinx.coroutines.launch

class MoviesListViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _mutableLiveDataMovies = MutableLiveData<List<Movie>>(emptyList())

    val liveDataMovies get() = _mutableLiveDataMovies

    fun loadMovies() {
        viewModelScope.launch {
            val movies = repository.loadMovies()
            _mutableLiveDataMovies.value = movies
        }
    }
}
