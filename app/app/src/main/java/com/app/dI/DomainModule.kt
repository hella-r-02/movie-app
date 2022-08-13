package com.app.dI

import com.app.data.MovieRepositoryImpl
import com.app.data.local.room.LocalDataSource
import com.app.data.remote.RetrofitDataSource
import com.app.domain.MovieRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideMovieRepository(
        localDataSource: LocalDataSource,
        retrofitDataSource: RetrofitDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(
            localDataSource = localDataSource,
            dataSource = retrofitDataSource
        )
    }
}