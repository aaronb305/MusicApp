package com.example.itunesapp.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.itunesapp.R

fun navigate(supportFragmentManager: FragmentManager, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.songNavigationContainer, fragment)
        .addToBackStack(fragment.id.toString())
        .commit()
}