package com.app.moviesDetails.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.data.MovieRepository
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
}