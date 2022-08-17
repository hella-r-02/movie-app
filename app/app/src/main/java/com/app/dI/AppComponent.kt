package com.app.dI

import com.app.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class, DomainModule::class, NetworkModule::class, RoomModule::class]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}