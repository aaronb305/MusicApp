package com.example.itunesapp.presenter

import android.content.Context
import android.util.Log
import com.example.itunesapp.restapi.SongRepository
import com.example.itunesapp.utils.NetworkMonitor
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SongPresenterClassical @Inject constructor(
    private var context: Context?,
    private val songRepository: SongRepository,
    private var networkMonitor: NetworkMonitor, // = NetworkMonitor(context),
    private val disposable: CompositeDisposable // = CompositeDisposable()
) : SongPresenterClassicalContract{

    private var songViewContract: SongViewContract? = null

    override fun initializePresenter(viewContract: SongViewContract) {
        songViewContract = viewContract
    }

    override fun getClassicSongs() {
        songViewContract?.loadingSongs(true)

        songRepository.getClassicalSongs().subscribe(
            { songs -> songViewContract?.songSuccess(songs)
//                viewContract?.songFailed(Throwable("ERROR NO INTERNET"))
            },
            { error -> songViewContract?.songFailed(error)
                Log.d("****", "throwable error")}
        ).apply {
            disposable.add(this)
        }
    }

    override fun destroy() {
        networkMonitor.unregisterNetworkMonitor()
        context = null
        songViewContract = null
        disposable.dispose()
    }

    override fun checkNetwork() {
        networkMonitor.registerNetworkMonitor()
    }

//    private fun doNetworkCallClassical() {
//        SongService.retrofitService.getClassicalSongs()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                { response -> viewContract?.songSuccess(response) },
//                { error -> viewContract?.songFailed(error) }
//            ).apply {
//                disposable.add(this)
//            }
//    }
}