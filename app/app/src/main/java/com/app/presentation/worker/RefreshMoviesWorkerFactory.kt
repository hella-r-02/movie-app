package com.app.presentation.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject

class RefreshMoviesWorkerFactory @Inject constructor(
    private val factory: RefreshMoviesWorker.Factory
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            RefreshMoviesWorker::class.java.name ->
                factory.create(appContext, workerParameters)
            else -> null
        }
    }

}