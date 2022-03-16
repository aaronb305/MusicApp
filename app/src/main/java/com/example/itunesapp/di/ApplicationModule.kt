package com.example.itunesapp.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(
    val applicationContext: Context
){
    @Provides
    fun providesApplicationContext() = applicationContext
}