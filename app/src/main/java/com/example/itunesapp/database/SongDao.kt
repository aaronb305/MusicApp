package com.example.itunesapp.database

import androidx.room.*
import com.example.itunesapp.model.Song
import com.example.itunesapp.model.Songs
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface SongDao {
    @Query("SELECT * FROM song")
    fun getAll() : Single<List<Song>>

    @Query("SELECT * FROM song WHERE mainGenre LIKE (:genre)")
    fun getAllByGenre(genre: String) : Single<List<Song>>

    @Insert
    fun insertSong(song: Song) : Completable

    @Insert
    fun insertAll(songs: List<Song>) : Completable

    @Delete
    fun deleteSong(song: Song) : Completable
}