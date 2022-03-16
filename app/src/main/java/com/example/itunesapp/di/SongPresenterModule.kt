package com.example.itunesapp.di

import android.content.Context
import com.example.itunesapp.presenter.SongPresenterClassical
import com.example.itunesapp.restapi.SongRepository
import com.example.itunesapp.utils.NetworkMonitor
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class SongPresenterModule() {

    @Provides
    fun providesNetworkMonitor(context : Context?) =  NetworkMonitor(context)

    @Provides
    fun providesCompositeDisposable() = CompositeDisposable()

    @Provides
    fun providesSongPresenter(
        context: Context?,
        songRepository: SongRepository,
        compositeDisposable: CompositeDisposable,
        networkMonitor: NetworkMonitor
    ) : SongPresenterClassical {
        return SongPresenterClassical(context, songRepository, networkMonitor ,compositeDisposable)
    }
}