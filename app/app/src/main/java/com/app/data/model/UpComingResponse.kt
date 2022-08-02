package com.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class UpComingResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val movies: List<MovieResponse>
)