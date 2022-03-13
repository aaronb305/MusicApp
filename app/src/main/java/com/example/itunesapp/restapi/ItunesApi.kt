package com.example.itunesapp.restapi

import com.example.itunesapp.model.Song
import com.example.itunesapp.model.Songs
import io.reactivex.Single
import retrofit2.http.GET

interface ItunesApi {
    @GET ("search?term=pop&amp;media=music&amp;entity=song&amp;limit=50")
    fun getPopSongs() : Single<Songs>

    @GET ("search?term=rock&amp;media=music&amp;entity=song&amp;limit=50")
    fun getRockSongs() : Single<Songs>

    @GET ("search?term=classic&amp;media=music&amp;entity=song&amp;limit=50")
    fun getClassicalSongs() : Single<Songs>

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }
}