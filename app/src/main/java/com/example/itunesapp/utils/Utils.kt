package com.example.itunesapp.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import java.io.IOException

fun playContentUri(context: Context, uri: Uri) {
    var mMediaPlayer: MediaPlayer? = null
    try {
        mMediaPlayer = MediaPlayer().apply {
            setDataSource(context, uri)
            setAudioAttributes(
                AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            )
            prepare()
            start()
        }
    } catch (exception: IOException) {
        mMediaPlayer?.release()
        mMediaPlayer = null
    }
}
