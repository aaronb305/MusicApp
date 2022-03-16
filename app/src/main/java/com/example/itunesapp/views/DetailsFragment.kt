package com.example.itunesapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itunesapp.R
import com.example.itunesapp.model.Song
import com.example.itunesapp.presenter.SongDetailsViewContract

class DetailsFragment : Fragment() , SongDetailsViewContract{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(song: Song) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}