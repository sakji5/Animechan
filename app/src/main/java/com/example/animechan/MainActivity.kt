package com.example.animechan

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.animechan.home.ui.HomeFragment
import com.example.animechan.random.ui.RandomFragment
import com.example.animechan.search.SearchFragment
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val container = R.id.fragment_container
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(HomeFragment())

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