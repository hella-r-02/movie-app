package com.app.data.local.room

import com.app.domain.model.Movie

interface LocalDataSource {
    suspend fun loadMovies(): List<Movie>
    fun insertMovies(movieFromNetwork: List<Movie>)
}