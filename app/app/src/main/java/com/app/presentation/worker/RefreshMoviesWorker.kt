package com.app.presentation.worker

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.work.Worker
import dagger.assisted.Assisted
import androidx.work.WorkerParameters
import com.app.domain.MovieRepository
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.runBlocking

class RefreshMoviesWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    val movieRepository: MovieRepository
) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        if (!isOnline()) {
            Log.e(TAG, "Failed to refresh movies")
            return Result.failure()
        }
        runBlocking {
            val movies = movieRepository.loadMoviesFromApi()
            movieRepository.insertMovieToDb(movies)
        }
        Log.e(TAG, "movies are refreshed")
        return Result.success()
    }

    private fun isOnline(): Boolean {
        if (context == null) return false
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

    @AssistedFactory
    interface Factory {
        fun create(appContext: Context, params: WorkerParameters): RefreshMoviesWorker
    }

    companion object {
        private val TAG = RefreshMoviesWorker::class.java.simpleName

        const val WORK_TAG = "REFRESH_MOVIES"
    }
}
