package com.example.itunesapp.presenter

import android.util.Log
import com.example.itunesapp.database.DatabaseRepository
import com.example.itunesapp.database.SongDatabase
import com.example.itunesapp.model.Song
import com.example.itunesapp.model.Songs
import com.example.itunesapp.restapi.SongRepository
import com.example.itunesapp.utils.NetworkMonitor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * communicates with [DatabaseRepository] and [SongRepository] to make network calls and check
 * network status
 */
class SongPresenterPop @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val songRepository: SongRepository,
    private val networkMonitor: NetworkMonitor,
    private val disposable: CompositeDisposable
) : SongPresenterPopContract {

    private var songViewContract: SongViewContract? = null

    override fun initializePresenter(viewContract: SongViewContract) {
        songViewContract = viewContract
    }

    /**
     * obtains classic songs from Api, or loads from database if offline
     */
    override fun getPopSongs() {
        Log.d("pop fragment", "get pop songs called")
        songViewContract?.loadingSongs(true)

        networkMonitor.networkState
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { netstate -> if (netstate) {
                    Log.d("Pop fragment", "networkstate $netstate")
                    doNetworkCallPop()
                }
                else {
                    offlineLoadFromDatabase()
//                    songViewContract?.songFailed(Throwable("ERROR NO INTERNET CONNECTION")
                }},
                { error ->
                    offlineLoadFromDatabase()
//                    songViewContract?.songFailed(error)
                }
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

    /**
     * checks network status
     */
    override fun checkNetwork() {
        networkMonitor.registerNetworkMonitor()
    }

    /**
     * communicates with api to obtain songs
     */
    private fun doNetworkCallPop() {
        Log.d("pop fragment", "starting network call")
        songRepository.getPopSongs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { songs ->
                    insertPopSongsToDatabase(songs.songs)
                    Log.d("Pop fragment", "${songs.resultCount} songs from api")
                },
                { error ->
                    offlineLoadFromDatabase()
                    Log.e("pop fragment", error.toString())
//                    songViewContract?.songFailed(error)
                }
            ).apply {
                disposable.add(this)
            }
    }

    /**
     * adds songs from [doNetworkCallClassical] to [SongDatabase]
     */
    private fun insertPopSongsToDatabase(songs: List<Song>) {
        Log.d("pop fragment", "entered insert songs")
        songs.forEach{
            it.genre = GENRE
            removeEmptyFields(it)
        }

        databaseRepository.insertAll(songs)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { getPopSongsFromDatabase()
                    Log.d("Pop fragment", "${songs.size} pop songs inserted") },
                { Log.e("Pop fragment", it.toString()) }
            ).apply {
                disposable.add(this)
            }
    }

    /**
     * retrieves songs from [SongDatabase] to populate recycle view
     */
    private fun getPopSongsFromDatabase() {
        databaseRepository.getAllByGenre(GENRE)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { songs ->
                    songViewContract?.songSuccess(songs)
                    Log.d("Pop fragment", "pop songs loaded") },
                { error ->
                    songViewContract?.songFailed(error)
                    Log.e("Pop fragment", error.toString()) }
            ).apply {
                disposable.add(this)
            }
    }

    /**
     * gets songs from [SongDatabase] to populate recycle view if offline
     */
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

    /**
     * deals with nullable/blank fields in api for certain songs in order to populate
     * recycle view
     */
    private fun removeEmptyFields(song: Song) : Song {
        if (song.contentAdvisoryRating.isNullOrEmpty()) {
            song.contentAdvisoryRating = ""
        }
        if (song.artworkUrl30.isNullOrEmpty()) {
            song.artworkUrl30 = ""
        }
        if (song.kind.isNullOrEmpty()) {
            song.kind = ""
        }
        if (song.trackCensoredName.isNullOrEmpty()) {
            song.trackCensoredName = ""
        }
        if (song.trackExplicitness.isNullOrEmpty()) {
            song.trackExplicitness = ""
        }
        if (song.trackName.isNullOrEmpty()) {
            song.trackName = ""
        }
        if (song.trackViewUrl.isNullOrEmpty()) {
            song.trackViewUrl = ""
        }
        if (song.artistViewUrl.isNullOrEmpty()) {
            song.artistViewUrl = ""
        }
        return song
    }

    companion object {
        const val GENRE = "pop"
    }
}

interface SongPresenterPopContract {
    fun initializePresenter(viewContract: SongViewContract)
    fun getPopSongs()
    fun destroy()
    fun checkNetwork()
}
