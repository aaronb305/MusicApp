package com.example.itunesapp.restapi

import com.example.itunesapp.model.Song
import io.reactivex.Single
import retrofit2.http.GET

interface ItunesApi {
    @GET ("search?term=pop&amp;media=music&amp;entity=song&amp;limit=50")
    fun getPopSongs() : Single<List<Song>>

    @GET ("search?term=rock&amp;media=music&amp;entity=song&amp;limit=50")
    fun getRockSongs() : Single<List<Song>>

    @GET ("search?term=classic&amp;media=music&amp;entity=song&amp;limit=50")
    fun getClassicalSongs() : Single<List<Song>>

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }
}