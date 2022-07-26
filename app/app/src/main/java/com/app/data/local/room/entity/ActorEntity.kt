package com.app.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "actors",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = MovieDetailsEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieDetailsId"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )
    ]
)

data class ActorEntity(
    @ColumnInfo(name = "actorId")
    val actorId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "imgUrl")
    val imageUrl: String?,
    @ColumnInfo(name = "movieDetailsId")
    val movieDetailsId: Int
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}