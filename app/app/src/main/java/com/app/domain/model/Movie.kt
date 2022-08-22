package com.app.domain.model

import java.io.Serializable

class Movie(
    val id: Int,
    val pgAge: Int,
    val title: String,
    var genres: List<Genre>,
    val runningTime: Int,
    val reviewCount: Int,
    var isLiked: Boolean,
    val rating: Int,
    val imageUrl: String,
) : Serializable