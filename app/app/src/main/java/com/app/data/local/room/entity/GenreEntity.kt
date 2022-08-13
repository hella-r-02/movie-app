package com.app.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "genres",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = CASCADE
        ),
        androidx.room.ForeignKey(
            entity = MovieDetailsEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieDetailsId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class GenreEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val genreId: Int,
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "movieId")
    val movieId: Int,
    @ColumnInfo(name = "movieDetailsId")
    val movieDetailsId: Int?
)