package com.example.itunesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.itunesapp.databinding.ActivityMainBinding
import com.example.itunesapp.views.ClassicFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val myOnNavigationItemClickListener =
//            NavigationBarView.OnItemSelectedListener { item ->
//                when (item.itemId) {
//                    R.id.popTab
//                }
//            }
    }
}