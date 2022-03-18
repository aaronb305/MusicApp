package com.example.itunesapp.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itunesapp.R

class PopFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("pop Fragment", "on create pop fragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("pop Fragment", "on create view pop fragment")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pop, container, false)
    }

    override fun onResume() {
        super.onResume()

        Log.d("pop Fragment", "on resume pop fragment")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("pop Fragment", "on destroy view pop fragment")
    }

    companion object {
        fun newInstance() = PopFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}