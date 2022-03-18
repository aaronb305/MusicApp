package com.example.itunesapp.presenter

import android.content.Context
import android.util.Log
import com.example.itunesapp.database.DatabaseRepository
import com.example.itunesapp.database.SongDao
import com.example.itunesapp.database.SongDatabase
import com.example.itunesapp.model.Song
import com.example.itunesapp.model.Songs
import com.example.itunesapp.restapi.SongRepository
import com.example.itunesapp.utils.NetworkMonitor
import com.example.itunesapp.utils.NetworkState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SongPresenterClassical @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val songRepository: SongRepository,
    private val networkMonitor: NetworkMonitor, // = NetworkMonitor(context),
    private val disposable: CompositeDisposable // = CompositeDisposable()
) : SongPresenterClassicalContract{

    private var songViewContract: SongViewContractClassical? = null

    override fun initializePresenter(viewContract: SongViewContractClassical) {
        songViewContract = viewContract
    }

    override fun getClassicSongs() {
        Log.d("Classic fragment", "beginning get classic songs")
        songViewContract?.loadingSongs(true)

        networkMonitor.networkState
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { netstate -> if (netstate) {
                    Log.d("Classic fragment", "networkstate $netstate")
                    doNetworkCallClassical()
                }
                else {
                    songViewContract?.songFailed(Throwable("ERROR NO INTERNET CONNECTION"))
                }},
                { error ->
                    offlineLoadFromDatabase()
                    songViewContract?.songFailed(error) }
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
        Log.d("Classic fragment", "starting network call")
        songRepository.getClassicalSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { songs ->
                    insertClassicSongsToDatabase(songs.songs)
                    Log.d("classic fragment", "${songs.resultCount} songs from api")
                },
                { error ->
                    offlineLoadFromDatabase()
                    Log.e("classic fragment", error.toString())
                    songViewContract?.songFailed(error) }
            ).apply {
                disposable.add(this)
            }
    }

    private fun insertClassicSongsToDatabase(songs: List<Song>) {
        Log.d("Classic fragment", "entered insert songs")
        songs.forEach{
            it.genre = GENRE
        }

        databaseRepository.insertAll(songs)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { getClassicSongsFromDatabase()
                    Log.d("Classic fragment", "${songs.size} classic songs inserted") },
                { Log.e("Classic fragment", it.toString()) }
            ).apply {
                disposable.add(this)
            }
    }

    private fun getClassicSongsFromDatabase() {
        databaseRepository.getAllByGenre(GENRE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { songs ->
                    songViewContract?.songSuccess(songs)
                    Log.d("Classic fragment", "classic songs loaded") },
                { error ->
                    songViewContract?.songFailed(error)
                    Log.e("Classic fragment", error.toString()) }
            ).apply {
                disposable.add(this)
            }
    }

    private fun offlineLoadFromDatabase() {
        databaseRepository.getAllByGenre(GENRE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { songs ->
                    songViewContract?.offlineLoad(songs) },
                { error ->
                    songViewContract?.songFailed(error)
                }
            ).apply {
                disposable.add(this)
            }
    }

    companion object {
        const val GENRE = "classic"
    }
}
interface SongPresenterClassicalContract {
    fun initializePresenter(viewContract: SongViewContractClassical)
    fun getClassicSongs()
    fun destroy()
    fun checkNetwork()
}

interface SongViewContractClassical {
    fun offlineLoad(songs: List<Song>)
    fun loadingSongs(isLoading: Boolean)
    fun songSuccess(songs: List<Song>)
    fun songFailed(throwable: Throwable)
}