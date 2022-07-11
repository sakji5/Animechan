package com.example.animechan.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.animechan.R
import com.example.animechan.ui.home.HomeFragment
import com.example.animechan.ui.random.RandomFragment
import com.example.animechan.ui.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val container = R.id.fragment_container
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.setOnItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.home_page -> replaceFragment(HomeFragment())
                R.id.search_page -> replaceFragment(SearchFragment())
                R.id.random_page -> replaceFragment(RandomFragment())
            }
            false
        }

    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(container, fragment).commit()
    }

}