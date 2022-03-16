package com.example.itunesapp.presenter

import android.util.Log
import com.example.itunesapp.model.Songs
import com.example.itunesapp.restapi.SongRepository
import com.example.itunesapp.utils.NetworkMonitor
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SongPresenterPop @Inject constructor(
    private val songRepository: SongRepository,
    private val networkMonitor: NetworkMonitor,
    private val disposable: CompositeDisposable
) : SongPresenterPopContract {

    private var songViewContract: SongViewContractPop? = null

    override fun initializePresenter(viewContract: SongViewContractPop) {
        songViewContract = viewContract
    }

    override fun getPopSongs() {
        // no-op
        Log.d("****", "get pop songs called")
    }

    override fun destroy() {
        // no-op
    }

    override fun checkNetwork() {
        // no-op
    }
}

interface SongPresenterPopContract {
    fun initializePresenter(viewContract: SongViewContractPop)
    fun getPopSongs()
    fun destroy()
    fun checkNetwork()
}

interface SongViewContractPop {
    fun loadingSongs(isLoading: Boolean)
    fun songSuccess(songs: Songs)
    fun songFailed(throwable: Throwable)
}