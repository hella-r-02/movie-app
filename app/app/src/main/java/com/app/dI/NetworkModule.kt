package com.app.dI

import com.app.data.service.ActorApiService
import com.app.data.service.ConfigurationApiService
import com.app.data.service.GenreApiService
import com.app.data.service.MovieApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

const val BASE_URL = "https://api.themoviedb.org/"
const val VERSION = "3/"
const val API_KEY = "917e372c9cf442c6ee69d5e4a17a3363"

class NetworkModule {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    private val okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL + VERSION)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val movieApi: MovieApiService = retrofit.create(MovieApiService::class.java)
    val genreApi: GenreApiService = retrofit.create(GenreApiService::class.java)
    val configApi: ConfigurationApiService = retrofit.create(ConfigurationApiService::class.java)
    val actorApi: ActorApiService = retrofit.create(ActorApiService::class.java)
}