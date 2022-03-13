package com.example.itunesapp.presenter

interface SongPresenterContract {
    fun getClassicSongs()
    fun getPopSongs()
    fun getRockSongs()
    fun destroy()
    fun checkNetwork()
}