package com.example.itunesapp.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itunesapp.R
import com.example.itunesapp.model.Song

class DetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("Details Fragment", "on create details fragment")
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
        fun newInstance(song: Song) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}