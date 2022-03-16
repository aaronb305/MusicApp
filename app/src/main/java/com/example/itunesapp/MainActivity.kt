package com.example.itunesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.itunesapp.utils.navigate
import com.example.itunesapp.views.ClassicFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigate(supportFragmentManager, ClassicFragment.newInstance())
    }
}