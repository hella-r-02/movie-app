package com.app.data.local.room.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.app.data.local.room.entity.ActorEntity
import com.app.data.local.room.entity.GenreEntity
import com.app.data.local.room.entity.MovieDetailsEntity

data class MovieDetailsWithGenresAndActors(
    @Embedded
    val movieDetailsEntity: MovieDetailsEntity,

    @Relation(parentColumn = "id", entityColumn = "id")
    val genres: List<GenreEntity>,

    @Relation(parentColumn = "id", entityColumn = "id")
    val actors: List<ActorEntity>
)