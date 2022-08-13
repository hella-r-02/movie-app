package com.app.dI

import com.app.data.remote.ImageUrlAppender
import com.app.data.remote.RetrofitDataSource
import com.app.data.remote.RetrofitDataSourceImpl
import com.app.data.remote.service.ActorApiService
import com.app.data.remote.service.ConfigurationApiService
import com.app.data.remote.service.GenreApiService
import com.app.data.remote.service.MovieApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

const val BASE_URL = "https://api.themoviedb.org/"
const val VERSION = "3/"
const val API_KEY = "917e372c9cf442c6ee69d5e4a17a3363"

@Module
class NetworkModule {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    @Singleton
    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL+ VERSION)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Singleton
    @Provides
    fun provideMovieAPI(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)

    }

    @Singleton
    @Provides
    fun provideGenreAPI(retrofit: Retrofit): GenreApiService {
        return retrofit.create(GenreApiService::class.java)

    }

    @Singleton
    @Provides
    fun provideConfigAPI(retrofit: Retrofit): ConfigurationApiService {
        return retrofit.create(ConfigurationApiService::class.java)

    }

    @Singleton
    @Provides
    fun provideActorAPI(retrofit: Retrofit): ActorApiService {
        return retrofit.create(ActorApiService::class.java)

    }

    @Singleton
    @Provides
    fun provideRetrofitDataSource(
        movieApiService: MovieApiService,
        imageUrlAppender: ImageUrlAppender,
        genreApiService: GenreApiService,
        actorApiService: ActorApiService
    ): RetrofitDataSource {
        return RetrofitDataSourceImpl(
            movieApiService = movieApiService,
            imageUrlAppender = imageUrlAppender,
            genreApiService = genreApiService,
            actorApiService = actorApiService
        )
    }
}