package com.app.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieActorsResponse(
    @SerialName("id") val id: Int,
    @SerialName("cast") val actors: List<ActorResponse>
)