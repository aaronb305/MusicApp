package com.example.itunesapp.database

import androidx.room.*
import androidx.room.RoomDatabase
import com.example.itunesapp.model.Song

/**
 * stores all fields from requests from [DatabaseRepository] and allows [SongDao] to pull and edit
 * fields in/from the database
 */
@Database(entities = [Song::class], version = 1)
abstract class SongDatabase : RoomDatabase() {
    abstract fun songDao() : SongDao
}