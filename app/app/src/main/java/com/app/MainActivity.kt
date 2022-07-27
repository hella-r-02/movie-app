package com.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

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

    private fun navigateToDetails() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutContainer, FragmentMoviesDetails())
            .addToBackStack(FragmentMoviesDetails::class.java.simpleName)
            .commit()
    }

    private fun navigateFromDetailsToList() {
        supportFragmentManager.popBackStack()
    }

    override fun onMovieSelected() {
        navigateToDetails()
    }

    override fun onClickBack() {
        navigateFromDetailsToList()
    }
}