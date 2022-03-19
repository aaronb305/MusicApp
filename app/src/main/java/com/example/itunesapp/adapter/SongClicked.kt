package com.example.itunesapp.adapter

import com.example.itunesapp.model.Song

/**
 * interface for recycle view click listener
 */
interface SongClicked {
    fun onSongClicked(song: Song)
}