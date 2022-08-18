package com.app.domain.model

data class MovieDetails(
    val id: Int,
    val pgAge: Int,
    val title: String,
    var genres: List<Genre>,
    val runningTime: Int,
    val reviewCount: Int,
    val isLiked:Boolean,
    val rating: Int,
    val imageUrl: String,
    val storyLine: String,
    var actors: List<Actor>
) {
}