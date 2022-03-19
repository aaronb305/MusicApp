package com.example.itunesapp.restapi

import com.example.itunesapp.model.Song
import com.example.itunesapp.model.Songs
import io.reactivex.Single
import javax.inject.Inject

/**
 * abstraction layer for [ItunesApi], ends up doing network calls for [ItunesApi]
 */
interface SongRepository {
    fun getClassicalSongs() : Single<Songs>
    fun getRockSongs() : Single<Songs>
    fun getPopSongs() : Single<Songs>
}

/**
 * implementation of [SongRepository], returns network calls from [ItunesApi]
 */
class SongRepositoryImpl @Inject constructor(
    private val songApi: ItunesApi
) : SongRepository {
    override fun getClassicalSongs(): Single<Songs> {
        return songApi.getClassicalSongs()
    }

    override fun getRockSongs(): Single<Songs> {
        return songApi.getRockSongs()
    }

    override fun getPopSongs(): Single<Songs> {
        return songApi.getPopSongs()
    }
}