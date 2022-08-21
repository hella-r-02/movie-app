package com.app.presentation.movieDetails.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.MovieRepository
import com.app.presentation.movieDetails.MovieState
import kotlinx.coroutines.launch

class MoviesDetailsViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _mutableLiveDataState = MutableLiveData<MovieState>(MovieState.DefaultState(null))
    val liveDataState get() = _mutableLiveDataState

    fun loadMovie(id: Int) {
        viewModelScope.launch {
            val movie = repository.loadMovie(id)
            if (movie == null) {
                _mutableLiveDataState.value = MovieState.ErrorState(null)
            } else {
                _mutableLiveDataState.value = MovieState.DefaultState(movie)
            }
        }
    }

    fun setNullMovie() {
        viewModelScope.launch {
            _mutableLiveDataState.value = MovieState.DefaultState(null)
        }
    }
}