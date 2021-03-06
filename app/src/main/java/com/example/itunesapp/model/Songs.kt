package com.example.itunesapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * holder class for [Song] data class
 */
data class Songs(
    @SerializedName("resultCount")
    val resultCount: Int,
    @SerializedName("results")
    val songs: List<Song>
)