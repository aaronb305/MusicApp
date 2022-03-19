package com.example.itunesapp.views

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.itunesapp.ItunesApp
import com.example.itunesapp.R
import com.example.itunesapp.adapter.SongAdapter
import com.example.itunesapp.databinding.FragmentClassicBinding
import com.example.itunesapp.model.Song
import com.example.itunesapp.presenter.SongPresenterClassical
import com.example.itunesapp.presenter.SongPresenterPop
import com.example.itunesapp.presenter.SongViewContract
import javax.inject.Inject

class PopFragment : Fragment(), SongViewContract{

    /**
     * injects presenter to be used
     */
    @Inject
    lateinit var songPresenterPop: SongPresenterPop

    /**
     * initializes media player
     */
    private val player by lazy {
        MediaPlayer()
    }

    private val binding by lazy {
        FragmentClassicBinding.inflate(layoutInflater)
    }

    /**
     * allows for preview of song to be played when clicked
     */
    private val songAdapter by lazy {
        SongAdapter(onSongClicked = {
            player.stop()
            player.reset()
            player.setDataSource(it.previewUrl)
            player.prepare()
            player.start()
//            player.stop()
//            player.reset()
//            playMusic(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("pop Fragment", "on create pop fragment")
    }

    /**
     * injects dependencies from [SongComponent], initializes [SongPresenterPop], and
     * initializes [SongAdapter]
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ItunesApp.songComponent.inject(this)

        songPresenterPop.initializePresenter(this)

        binding.myRecycler.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = songAdapter
        }

        songPresenterPop.checkNetwork()

        Log.d("pop Fragment", "on create view pop fragment")
        // Inflate the layout for this fragment
        return binding.root
    }

    /**
     * retrieves songs from api via [SongPresenterPop] and sets up swipe to refresh
     */
    override fun onResume() {
        super.onResume()

        Log.d("pop Fragment", "on resume pop fragment")

        songPresenterPop.getPopSongs()
        binding.swipeToRefresh.setOnRefreshListener {
            songPresenterPop.getPopSongs()
            binding.swipeToRefresh.isRefreshing = true
        }
    }

    /**
     * destroys [SongPresenterPop]
     */
    override fun onDestroyView() {
        super.onDestroyView()

        Log.d("pop Fragment", "on destroy view pop fragment")

        songPresenterPop.destroy()
    }

    /**
     * loads songs when internet connections is absent
     */
    override fun offlineLoad(songs: List<Song>) {
        binding.myRecycler.visibility = View.VISIBLE
        binding.loadingBar.visibility = View.GONE
        songAdapter.updateSongs(songs)
        binding.swipeToRefresh.isRefreshing = false
    }

    /**
     * shows loading bar while waiting for songs to be pulled
     */
    override fun loadingSongs(isLoading: Boolean) {
        binding.myRecycler.visibility = View.GONE
        binding.loadingBar.visibility = View.VISIBLE
    }

    /**
     * adds songs to recycle view via [SongAdapter]
     */
    override fun songSuccess(songs: List<Song>) {
        binding.myRecycler.visibility = View.VISIBLE
        binding.loadingBar.visibility = View.GONE
        songAdapter.updateSongs(songs)
        binding.swipeToRefresh.isRefreshing = false
    }

    /**
     * lets user know api call failed
     */
    override fun songFailed(throwable: Throwable) {
        binding.myRecycler.visibility = View.GONE
        binding.loadingBar.visibility = View.GONE

        AlertDialog.Builder(requireContext())
            .setTitle("AN ERROR HAS OCCURRED")
            .setMessage(throwable.localizedMessage)
            .setPositiveButton("DISMISS") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    companion object {
        fun newInstance() =
            PopFragment()
    }
}