package com.app.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "actors",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)

data class ActorEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val actorId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "imgUrl")
    val imageUrl: String,
    @ColumnInfo(name = "movieId")
    val movieId: Int

)