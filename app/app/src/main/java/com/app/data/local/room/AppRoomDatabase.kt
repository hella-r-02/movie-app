package com.app.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.data.local.room.dao.ActorDao
import com.app.data.local.room.dao.GenreDao
import com.app.data.local.room.dao.MovieDao
import com.app.data.local.room.dao.MovieDetailsDao
import com.app.data.local.room.entity.*

@Database(
    entities = [MovieEntity::class, GenreEntity::class, ActorEntity::class, MovieDetailsEntity::class],
    version = 1
)
abstract class AppRoomDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "Movies.db"
    }

    abstract fun getMovieDao(): MovieDao
    abstract fun getGenreDao(): GenreDao
    abstract fun getActorDao(): ActorDao
    abstract fun getMovieDetailsDao(): MovieDetailsDao
}