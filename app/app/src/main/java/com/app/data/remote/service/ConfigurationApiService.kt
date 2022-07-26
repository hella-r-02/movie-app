package com.app.data.remote.service

import com.app.dI.API_KEY
import com.app.data.remote.model.ConfigurationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ConfigurationApiService {
    @GET("configuration?")
    suspend fun getConfiguration(@Query("api_key") apiKey: String = API_KEY): ConfigurationResponse
}