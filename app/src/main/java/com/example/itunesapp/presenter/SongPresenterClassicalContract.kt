package com.example.itunesapp.presenter

interface SongPresenterClassicalContract {
    fun initializePresenter(viewContract: SongViewContract)
    fun getClassicSongs()
//    fun getPopSongs()
//    fun getRockSongs()
    fun destroy()
    fun checkNetwork()
}