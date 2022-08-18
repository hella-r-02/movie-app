package com.app.presentation.worker

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.app.App
import com.app.domain.MovieRepository
import javax.inject.Inject

class RefreshMoviesWorker(
    private val context: Context,
    workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    @Inject
    lateinit var movieRepository: MovieRepository

    init {
        val injector = (context.applicationContext as App).appComponent
        injector.inject(this)
    }

    override suspend fun doWork(): Result {
        if (!isOnline()) {
            Log.e(TAG, "Failed to refresh movies")
            return Result.failure()
        }
        val movies = movieRepository.loadMoviesFromApi()
        movieRepository.insertMoviesToDb(movies)

        Log.e(TAG, "movies are refreshed")
        return Result.success()
    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    companion object {
        private val TAG = RefreshMoviesWorker::class.java.simpleName

        const val WORK_TAG = "REFRESH_MOVIES"
    }
}
