package com.app.data.models

import java.io.Serializable

data class Movie(
    val id: Int,
    val name: String,
    val poster: String,
    val pg: String,
    val rating: Int,
    val storyline: String,
    val countOfReviews: String,
    val duration: String,
    val actors: List<Actor>,
    val genre: String
) : Serializable