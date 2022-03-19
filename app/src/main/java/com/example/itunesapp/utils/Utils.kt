package com.example.itunesapp.utils

import android.media.MediaPlayer
import com.example.itunesapp.model.Song

/**
 * tried to implement a seperate function to play music but it did not work but I do not understand
 * why it failed. Issue was the previous song was not stopping when next song is played
 */
fun playMusic(song: Song) {
    val player = MediaPlayer()
    player.stop()
    player.reset()
    player.setDataSource(song.previewUrl)
    player.prepare()
    player.start()
}