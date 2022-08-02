package com.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("poster_path") val posterPath: String,
    @SerialName("adult") val adult: Boolean,
    @SerialName("genre_ids") val genreIds: List<Int>,
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("vote_average") val voteAverage: Double
)