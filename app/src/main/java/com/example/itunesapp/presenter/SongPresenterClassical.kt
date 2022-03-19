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

/**
 * communicates with [DatabaseRepository] and [SongRepository] to make network calls and check
 * network status
 */
class SongPresenterClassical @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val songRepository: SongRepository,
    private val networkMonitor: NetworkMonitor, // = NetworkMonitor(context),
    private val disposable: CompositeDisposable // = CompositeDisposable()
) : SongPresenterClassicalContract{

    private var songViewContract: SongViewContract? = null

    override fun initializePresenter(viewContract: SongViewContract) {
        songViewContract = viewContract
    }

    /**
     * obtains classic songs from Api, or loads from database if offline
     */
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
                    offlineLoadFromDatabase()
//                    songViewContract?.songFailed(Throwable("ERROR NO INTERNET CONNECTION"))
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

    /**
     * adds songs from [doNetworkCallClassical] to [SongDatabase]
     */
    private fun insertClassicSongsToDatabase(songs: List<Song>) {
        Log.d("Classic fragment", "entered insert songs")
        songs.forEach{
            it.genre = GENRE
            removeEmptyFields(it)
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

    /**
     * retrieves songs from [SongDatabase] to populate recycle view
     */
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
        const val GENRE = "classic"
    }
}
interface SongPresenterClassicalContract {
    fun initializePresenter(viewContract: SongViewContract)
    fun getClassicSongs()
    fun destroy()
    fun checkNetwork()
}

interface SongViewContract {
    fun offlineLoad(songs: List<Song>)
    fun loadingSongs(isLoading: Boolean)
    fun songSuccess(songs: List<Song>)
    fun songFailed(throwable: Throwable)
}