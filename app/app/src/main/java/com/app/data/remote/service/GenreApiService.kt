package com.app.data.remote.service

import com.app.dI.API_KEY
import com.app.data.remote.model.GenresResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreApiService {
    @GET("genre/movie/list?")
    suspend fun getGenres(@Query("api_key") apiKey: String = API_KEY): GenresResponse
}