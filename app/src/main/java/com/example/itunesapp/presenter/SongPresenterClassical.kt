package com.example.itunesapp.presenter

import android.content.Context
import android.util.Log
import com.example.itunesapp.model.Songs
import com.example.itunesapp.restapi.SongRepository
import com.example.itunesapp.utils.NetworkMonitor
import com.example.itunesapp.utils.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SongPresenterClassical @Inject constructor(
    private val songRepository: SongRepository,
    private val networkMonitor: NetworkMonitor, // = NetworkMonitor(context),
    private val disposable: CompositeDisposable // = CompositeDisposable()
) : SongPresenterClassicalContract{

    private var songViewContract: SongViewContractClassical? = null

    override fun initializePresenter(viewContract: SongViewContractClassical) {
        songViewContract = viewContract
    }

    override fun getClassicSongs() {
        songViewContract?.loadingSongs(true)

        networkMonitor.networkState
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { netstate -> if (netstate) {
                    doNetworkCallClassical()
                }
                else {
                    songViewContract?.songFailed(Throwable("ERROR NO INTERNET CONNECTION"))
                }},
                { songViewContract?.songFailed(it) }
            )
            .apply {
                disposable.add(this)
            }
    }

    override fun destroy() {
        networkMonitor.unregisterNetworkMonitor()
        songViewContract = null
        disposable.dispose()
    }

    override fun checkNetwork() {
        networkMonitor.registerNetworkMonitor()
    }

    private fun doNetworkCallClassical() {
        songRepository.getClassicalSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { songs -> songViewContract?.songSuccess(songs) },
                { error -> songViewContract?.songFailed(error) }
            ).apply {
                disposable.add(this)
            }
    }
}
interface SongPresenterClassicalContract {
    fun initializePresenter(viewContract: SongViewContractClassical)
    fun getClassicSongs()
    fun destroy()
    fun checkNetwork()
}

interface SongViewContractClassical {
    fun loadingSongs(isLoading: Boolean)
    fun songSuccess(songs: Songs)
    fun songFailed(throwable: Throwable)
}