package com.example.itunesapp.views

import android.app.AlertDialog
import android.os.Bundle
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
import com.example.itunesapp.model.Songs
import com.example.itunesapp.presenter.SongPresenterClassical
import com.example.itunesapp.presenter.SongViewContractClassical
import com.example.itunesapp.utils.navigate
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class ClassicFragment : Fragment(), SongViewContractClassical{

    @Inject
    lateinit var songPresenterClassical: SongPresenterClassical

    private val binding by lazy {
        FragmentClassicBinding.inflate(layoutInflater)
    }

    private val songAdapter by lazy {
        SongAdapter(onSongClicked = {
            navigate(supportFragmentManager =
            requireActivity().supportFragmentManager, DetailsFragment.newInstance(it))
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ItunesApp.songComponent.inject(this)

        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        songPresenterClassical.initializePresenter(this)
        binding.myRecycler.apply {
            layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = songAdapter
        }

        songPresenterClassical.checkNetwork()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        songPresenterClassical.getClassicSongs()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        songPresenterClassical.destroy()
    }

    override fun loadingSongs(isLoading: Boolean) {
        binding.myRecycler.visibility = View.GONE
        binding.loadingBar.visibility = View.VISIBLE
    }

    override fun songSuccess(songs: Songs) {
        binding.myRecycler.visibility = View.VISIBLE
        binding.loadingBar.visibility = View.GONE
        songAdapter.updateSongs(songs)
    }

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
        @JvmStatic
        fun newInstance() = ClassicFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}