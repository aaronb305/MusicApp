package com.example.itunesapp.presenter

import com.example.itunesapp.model.Songs
import com.example.itunesapp.restapi.SongRepository
import com.example.itunesapp.utils.NetworkMonitor
import io.reactivex.disposables.CompositeDisposable

class SongPresenterRock(
    private val songRepository: SongRepository,
    private val networkMonitor: NetworkMonitor,
    private val disposable: CompositeDisposable
) : SongPresenterRockContract {

    private var songViewContract: SongViewContractRock? = null

    override fun initializePresenter(viewContract: SongViewContractRock) {
        songViewContract = viewContract
    }

    override fun getRockSongs() {
        // no-op
    }

    override fun destroy() {
        // no-op
    }

    override fun checkNetwork() {
        // no-op
    }
}

interface SongPresenterRockContract {
    fun initializePresenter(viewContract: SongViewContractRock)
    fun getRockSongs()
    fun destroy()
    fun checkNetwork()
}

interface SongViewContractRock {
    fun loadingSongs(isLoading: Boolean)
    fun songSuccess(songs: Songs)
    fun songFailed(throwable: Throwable)
}