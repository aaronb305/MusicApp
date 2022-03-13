package com.example.itunesapp.presenter

import android.content.Context
import com.example.itunesapp.restapi.SongService
import com.example.itunesapp.utils.NetworkMonitor
import com.example.itunesapp.utils.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SongPresenter(
    private var context: Context? = null,
    private var viewContract: SongViewContract? = null,
    private var networkMonitor: NetworkMonitor = NetworkMonitor(context),
    private val disposable: CompositeDisposable = CompositeDisposable()
) : SongPresenterContract{

    override fun getClassicSongs() {
        viewContract?.loadingSongs(true)

        NetworkState.observeNetworkState.subscribe(
            { netState -> if(netState) {
                doNetworkCallClassical()
            } },
            { error -> viewContract?.songFailed(error) }
        ).apply {
            disposable.add(this)
        }
    }

    override fun getPopSongs() {
        viewContract?.loadingSongs(true)

        NetworkState.observeNetworkState.subscribe(
            { netState -> if(netState) {
                doNetworkCallPop()
            } },
            { error -> viewContract?.songFailed(error) }
        ).apply {
            disposable.add(this)
        }
    }

    override fun getRockSongs() {
        viewContract?.loadingSongs(true)

        NetworkState.observeNetworkState.subscribe(
            { netState -> if(netState) {
                doNetworkCallRock()
            } },
            { error -> viewContract?.songFailed(error) }
        ).apply {
            disposable.add(this)
        }
    }

    override fun destroy() {
        networkMonitor.unregisterNetworkMonitor()
        context = null
        viewContract = null
        disposable.dispose()
    }

    override fun checkNetwork() {
        networkMonitor.registerNetworkMonitor()
    }

    private fun doNetworkCallClassical() {
        SongService.retrofitService.getClassicalSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> viewContract?.songSuccess(response) },
                { error -> viewContract?.songFailed(error) }
            ).apply {
                disposable.add(this)
            }
    }

    private fun doNetworkCallPop() {
        SongService.retrofitService.getPopSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> viewContract?.songSuccess(response) },
                { error -> viewContract?.songFailed(error) }
            ).apply {
                disposable.add(this)
            }
    }
    private fun doNetworkCallRock() {
        SongService.retrofitService.getRockSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response -> viewContract?.songSuccess(response) },
                { error -> viewContract?.songFailed(error) }
            ).apply {
                disposable.add(this)
            }
    }
}