package com.app.dI

import android.content.Context
import androidx.room.Room
import com.app.data.local.room.AppRoomDatabase
import com.app.data.local.room.LocalDataSource
import com.app.data.local.room.RoomDataSource
import dagger.Module
import dagger.Provides

@Module
class RoomModule {
    @Provides
    fun provideAppRoomDatabase(context: Context) =
        Room.databaseBuilder(
            context,
            AppRoomDatabase::class.java,
            AppRoomDatabase.DATABASE_NAME
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Provides
    fun providesLocalDataSource(db: AppRoomDatabase): LocalDataSource = RoomDataSource(db)
}