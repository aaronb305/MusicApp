package com.example.itunesapp.presenter

import com.example.itunesapp.model.Songs

interface SongViewContract {
    fun loadingSongs(isLoading: Boolean)
    fun songSuccess(songs: Songs)
    fun songFailed(throwable: Throwable)
}