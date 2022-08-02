package com.app.data.service

import com.app.dI.API_KEY
import com.app.data.model.MovieDetailsResponse
import com.app.data.model.UpComingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("movie/upcoming?language=en-US&page=1")
    suspend fun getUpComing(@Query("api_key") apiKey: String = API_KEY): UpComingResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailsResponse
}