package com.example.itunesapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.itunesapp.model.Song
import com.example.itunesapp.model.Songs
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface SongDao {
    @Query("SELECT * FROM song")
    fun getAll() : Single<Songs>

    @Query("SELECT * FROM song WHERE mainGenre LIKE (:genre)")
    fun getAllByGenre(genre: String) : Single<Songs>

    @Insert
    fun insertSong(song: Song) : Completable

    @Insert
    fun insertAll(songs: Songs) : Completable

    @Delete
    fun deleteSong(song: Song) : Completable
}