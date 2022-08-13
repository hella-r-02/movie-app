package com.app.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.app.data.local.room.entity.ActorEntity

@Dao
interface ActorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(actorEntity: ActorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActors(items: Iterable<ActorEntity>)
}