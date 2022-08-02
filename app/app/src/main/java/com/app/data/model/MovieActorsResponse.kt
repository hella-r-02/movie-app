package com.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieActorsResponse(
    @SerialName("id") val id: Int,
    @SerialName("cast") val actors: List<ActorResponse>
)