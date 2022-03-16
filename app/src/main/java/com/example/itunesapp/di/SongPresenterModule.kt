package com.example.itunesapp.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.example.itunesapp.presenter.SongPresenterClassical
import com.example.itunesapp.restapi.SongRepository
import com.example.itunesapp.utils.NetworkMonitor
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class SongPresenterModule {

    @Provides
    fun providesNetworkMonitor(
        networkRequest: NetworkRequest,
        connectivityManager: ConnectivityManager
    ) : NetworkMonitor =  NetworkMonitor(networkRequest, connectivityManager)

    @Provides
    fun providesCompositeDisposable() = CompositeDisposable()

    @Provides
    fun providesSongPresenter(
        songRepository: SongRepository,
        compositeDisposable: CompositeDisposable,
        networkMonitor: NetworkMonitor
    ) : SongPresenterClassical {
        return SongPresenterClassical(songRepository, networkMonitor ,compositeDisposable)
    }
}