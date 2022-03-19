package com.example.itunesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesapp.R
import com.example.itunesapp.model.Song
import com.example.itunesapp.model.Songs
import com.squareup.picasso.Picasso

/**
 * [SongAdapter] used to add new songs to the recycle view and interact with the [SongViewHolder]
 */
class SongAdapter(
    private val songs: MutableList<Song> = mutableListOf(),
    private val onSongClicked: (Song) -> Unit
) : RecyclerView.Adapter<SongViewHolder>() {

    /**
     *  [updateSongs] removes all previous songs, and adds songs from api to recycle view
     */
    fun updateSongs(newSongs: List<Song>) {
        songs.clear()
        songs.addAll(newSongs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val songView = LayoutInflater.from(parent.context)
            .inflate(R.layout.song_item, parent, false)
        return SongViewHolder(songView, onSongClicked)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song)
    }

    override fun getItemCount(): Int = songs.size
}

/**
 * [SongViewHolder] binds each individual song to the song item in the card view and set
 * the appropriate fields
 */
class SongViewHolder(
    itemView: View,
    private val onSongClicked: (Song) -> Unit
) : RecyclerView.ViewHolder(itemView){

    /**
     * sets fields
     */
    private val songName : TextView = itemView.findViewById(R.id.songName)
    private val songPrice : TextView = itemView.findViewById(R.id.price)
    private val artist : TextView = itemView.findViewById(R.id.artistName)
    private val albumCover : ImageView = itemView.findViewById(R.id.albumCover)

    /**
     * binds fields to card view
     */
    fun bind(song: Song) {
        songName.text = song.trackName
        val priceSong = song.trackPrice.toString()
        songPrice.text = "\$$priceSong"
        artist.text = song.artistName

        itemView.setOnClickListener {
            onSongClicked.invoke(song)
        }

        /**
         * loads image
         */
        Picasso.get()
            .load(song.artworkUrl100)
            .placeholder(R.drawable.ic_baseline_downloading)
            .error(R.drawable.ic_baseline_error_outline)
            .fit()
            .into(albumCover)
    }

}