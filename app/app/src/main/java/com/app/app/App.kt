package com.app.app

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.work.*
import com.app.dI.AppComponent
import com.app.dI.AppModule
import com.app.dI.DaggerAppComponent
import com.app.presentation.worker.RefreshMoviesWorker
import com.app.presentation.worker.RefreshMoviesWorkerFactory
import com.app.presentation.worker.RefreshMoviesWorker_Factory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class App : Application() {
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var workerFactory: RefreshMoviesWorkerFactory

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
        WorkManager.initialize(
            this,
            Configuration.Builder().setWorkerFactory(workerFactory).build()
        )
    }
}

