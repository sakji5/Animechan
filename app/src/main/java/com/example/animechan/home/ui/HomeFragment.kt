package com.example.animechan.home.ui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animechan.R
import com.example.animechan.utils.BaseFragment

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var adapter: HomeRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val homeViewModel = viewModel as HomeViewModel
        homeViewModel.getAnimeList()

        adapter = HomeRecyclerViewAdapter(mutableListOf())

        initRecyclerView(view, R.id.home_recycler_view, adapter)
        initViews(view)
        observers()

        retryButton.setOnClickListener { homeViewModel.getAnimeList() }

        (viewModel as HomeViewModel).animeList.observe(viewLifecycleOwner) {
            adapter.addData(it)
            recyclerView.adapter?.notifyDataSetChanged()
        }

    }

    private fun initViews(view: View) {
        errorView = view.findViewById(R.id.home_error_view)
        progressBar = view.findViewById(R.id.home_progress_bar)
        errorTV = view.findViewById(R.id.home_error_message_tv)
        retryButton = view.findViewById(R.id.home_retry_btn)
    }
}