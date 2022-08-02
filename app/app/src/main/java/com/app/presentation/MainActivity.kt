package com.app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.R
import com.app.model.Movie
import com.app.presentation.movieDetails.FragmentMoviesDetails
import com.app.presentation.moviesList.FragmentMoviesList

class MainActivity : AppCompatActivity(),
    FragmentMoviesList.MoviesListItemClickListener,
    FragmentMoviesDetails.MoviesDetailsButtonClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
}