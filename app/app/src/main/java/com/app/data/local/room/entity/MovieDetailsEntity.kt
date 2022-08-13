package com.app.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movies_details"
)
data class MovieDetailsEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")

    val movieDetailsId: Int,
    @ColumnInfo(name = "pgAge")
    val pgAge: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "runningTime")
    val runningTime: Int,
    @ColumnInfo(name = "reviewCount")
    val reviewCount: Int,
    @ColumnInfo(name = "isLiked")
    val isLiked: Boolean,
    @ColumnInfo(name = "rating")
    val rating: Int,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
    @ColumnInfo(name = "storyLine")
    val storyLine: String
)