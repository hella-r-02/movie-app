package com.app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.app.R
import com.app.app.App
import com.app.domain.model.Movie
import com.app.presentation.movieDetails.FragmentMoviesDetails
import com.app.presentation.movieDetails.viewModel.MoviesDetailsViewModel
import com.app.presentation.movieDetails.viewModel.MoviesDetailsViewModelFactory
import com.app.presentation.moviesList.FragmentMoviesList
import com.app.presentation.moviesList.viewModel.MoviesListViewModel
import com.app.presentation.moviesList.viewModel.MoviesListViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
    FragmentMoviesList.MoviesListItemClickListener,
    FragmentMoviesDetails.MoviesDetailsButtonClickListener {
    @Inject
    lateinit var movieListViewModelFactory: MoviesListViewModelFactory

    @Inject
    lateinit var movieDetailsViewModelFactory: MoviesDetailsViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as App).appComponent.inject(this)
        if (savedInstanceState == null) {
            supportFragmentManager.apply {
                beginTransaction()
                    .replace(R.id.frameLayoutContainer, FragmentMoviesList())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun navigateToDetails(movie: Movie) {
        val bundle = Bundle()
        bundle.putInt("movie_id", movie.id)
        val fragment = FragmentMoviesDetails()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateFromDetailsToList() {
        supportFragmentManager.popBackStack()
    }

    override fun onMovieSelected(movie: Movie) {
        navigateToDetails(movie)
    }

    override fun onClickBack() {
        navigateFromDetailsToList()
    }

    fun getMovieListViewModel(): MoviesListViewModel =
        ViewModelProvider(this, movieListViewModelFactory).get(MoviesListViewModel::class.java)

    fun getMovieDetailsViewModel(): MoviesDetailsViewModel =
        ViewModelProvider(this, movieDetailsViewModelFactory).get(MoviesDetailsViewModel::class.java)

}