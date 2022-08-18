package com.app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.app.R
import com.app.app.App
import com.app.domain.model.Movie
import com.app.presentation.movieDetails.FragmentMoviesDetails
import com.app.presentation.movieDetails.viewModel.MoviesDetailsViewModel
import com.app.presentation.movieDetails.viewModel.MoviesDetailsViewModelFactory
import com.app.presentation.moviesList.FragmentMoviesList
import com.app.presentation.moviesList.viewModel.MoviesListViewModel
import com.app.presentation.moviesList.viewModel.MoviesListViewModelFactory
import com.app.presentation.worker.RefreshMoviesWorker
import java.util.concurrent.TimeUnit
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
        initWorkManager()
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

    private fun initWorkManager() {
        val workManager = WorkManager.getInstance(this)
        val work =
            PeriodicWorkRequestBuilder<RefreshMoviesWorker>(
                15,
                TimeUnit.MINUTES,
            ).build()
        workManager.enqueueUniquePeriodicWork(
            RefreshMoviesWorker.WORK_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            work
        )
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
        ViewModelProvider(this, movieListViewModelFactory)[MoviesListViewModel::class.java]

    fun getMovieDetailsViewModel(): MoviesDetailsViewModel =
        ViewModelProvider(
            this,
            movieDetailsViewModelFactory
        )[MoviesDetailsViewModel::class.java]

}