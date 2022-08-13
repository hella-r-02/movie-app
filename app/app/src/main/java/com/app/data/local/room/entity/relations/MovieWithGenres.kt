package com.app.data.local.room.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.app.data.local.room.entity.GenreEntity
import com.app.data.local.room.entity.MovieEntity

data class MovieWithGenres(
    @Embedded
    val movieEntity: MovieEntity,

    @Relation(parentColumn = "id", entityColumn = "id")
    val genres: List<GenreEntity>
)