package com.app.dI

import android.content.Context
import com.app.domain.MovieRepository
import com.app.presentation.movieDetails.viewModel.MoviesDetailsViewModelFactory
import com.app.presentation.moviesList.viewModel.MoviesListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideAnimalListViewModelFactory(movieRepository: MovieRepository): MoviesListViewModelFactory {
        return MoviesListViewModelFactory(movieRepository)
    }

    @Provides
    fun provideAnimalDetailsViewModelFactory(animalRepository: MovieRepository): MoviesDetailsViewModelFactory {
        return MoviesDetailsViewModelFactory(animalRepository)
    }
}