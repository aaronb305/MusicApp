package com.example.itunesapp.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itunesapp.R

class RockFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("rock Fragment", "on create rock fragment")
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("rock Fragment", "on create view rock fragment")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rock, container, false)
    }

    override fun onResume() {
        super.onResume()

        Log.d("rock Fragment", "on resume rock fragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("rock Fragment", "on destroy view rock fragment")
    }
    companion object {
        @JvmStatic
        fun newInstance() = RockFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}