package com.app.app

import android.app.Application
import com.app.dI.AppComponent
import com.app.dI.AppModule
import com.app.dI.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }
}

