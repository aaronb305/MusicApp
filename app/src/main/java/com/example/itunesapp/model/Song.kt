package com.example.itunesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Song(
    @PrimaryKey @SerializedName("trackId") val trackId: Int,
    @SerializedName("trackName") val songName: String,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("collectionName") val collectionName: String,
    @SerializedName("artworkUrl100") val artworkURL: String,
    @SerializedName("trackPrice") val trackPrice: Double,
    @SerializedName("previewUrl") val previewUrl: String,
    @SerializedName("primaryGenreName") val genre: String
)