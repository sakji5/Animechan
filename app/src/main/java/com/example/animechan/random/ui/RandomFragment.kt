package com.example.animechan.random.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animechan.R
import com.example.animechan.random.model.RandomQuote

class RandomFragment : Fragment(R.layout.fragment_random) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTV: TextView
    private lateinit var errorView: LinearLayout
    private lateinit var retryButton: Button

    private val quotesListEmptyItem = RandomQuote("","","")

    private lateinit var adapter: RandomRecyclerViewAdapter

    private lateinit var viewModel: RandomQuoteViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[RandomQuoteViewModel::class.java]
        initRecyclerView(view)
        initViews(view)
        observers()

    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.random_recycler_view)
        adapter = RandomRecyclerViewAdapter(mutableListOf(quotesListEmptyItem)) { viewModel.getQuotes() }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    }



    private fun initViews(view: View) {
        errorView = view.findViewById(R.id.random_error_view)
        progressBar = view.findViewById(R.id.random_progress_bar)
        errorTV = view.findViewById(R.id.random_error_message_tv)
        retryButton = view.findViewById(R.id.random_retry_btn)
    }

    private fun observers() {
        viewModel.progressBarState.observe(viewLifecycleOwner) { progressBar.visibility = it }

        viewModel.quotes.observe(viewLifecycleOwner) {
            val data: MutableList<RandomQuote> = mutableListOf()
            data.add(quotesListEmptyItem)
            data.addAll(it)
            adapter.addData(data)
            recyclerView.adapter?.notifyDataSetChanged()
        }

        viewModel.errorMessageState.observe(viewLifecycleOwner) {
            errorView.visibility = it.viewVisibility
            errorTV.text = it.errorMessage
        }
    }

}