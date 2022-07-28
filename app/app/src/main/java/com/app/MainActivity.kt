package com.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.data.models.Movie
import com.app.moviesDetails.FragmentMoviesDetails
import com.app.moviesList.FragmentMoviesList

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
        bundle.putSerializable("movie", movie)
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