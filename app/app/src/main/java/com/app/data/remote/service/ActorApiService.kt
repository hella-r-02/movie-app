package com.app.data.remote.service

import com.app.dI.API_KEY
import com.app.data.remote.model.MovieActorsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActorApiService {
    @GET("movie/{movie_id}/credits")
    suspend fun getActors(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieActorsResponse
}