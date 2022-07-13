package com.example.animechan.home.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animechan.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTV: TextView
    private lateinit var errorView: LinearLayout
    private lateinit var retryButton: Button

    private lateinit var viewModel: HomeViewModel
    private val animeList: MutableList<String> = mutableListOf()
    private lateinit var adapter: HomeRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getAnimeList()

        adapter = HomeRecyclerViewAdapter(animeList)

        initRecyclerView(view)
        initViews(view)

        observers()

        retryButton.setOnClickListener { viewModel.getAnimeList() }

    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.home_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun initViews(view: View) {
        errorView = view.findViewById(R.id.home_error_view)
        progressBar = view.findViewById(R.id.home_progress_bar)
        errorTV = view.findViewById(R.id.home_error_message_tv)
        retryButton = view.findViewById(R.id.home_retry_btn)
    }

    private fun observers() {
        viewModel.progressBarState.observe(viewLifecycleOwner) { progressBar.visibility = it }

        viewModel.animeList.observe(viewLifecycleOwner) {
            animeList.clear()
            animeList.addAll(it)
            adapter.addData(it)
            recyclerView.adapter?.notifyDataSetChanged()
        }

        viewModel.errorMessageState.observe(viewLifecycleOwner) {
            errorView.visibility = it.viewVisibility
            errorTV.text = it.errorMessage
        }
    }
}