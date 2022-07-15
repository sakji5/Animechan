package com.example.animechan.random.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.animechan.R
import com.example.animechan.random.model.Quote
import com.example.animechan.utils.BaseFragment

class RandomFragment : BaseFragment(R.layout.fragment_random) {

    private val quotesListEmptyItem = Quote("","","")

    private lateinit var adapter: RandomRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RandomRecyclerViewAdapter(mutableListOf(quotesListEmptyItem)){(viewModel as RandomQuoteViewModel).getQuotes() }

        viewModel = ViewModelProvider(this)[RandomQuoteViewModel::class.java]
        val randomViewModel = viewModel as RandomQuoteViewModel
        initRecyclerView(view, R.id.random_recycler_view, adapter, true)
        initViews(view)

        retryButton.setOnClickListener { randomViewModel.getQuotes() }

        randomViewModel.quotes.observe(viewLifecycleOwner) {
            val data: MutableList<Quote> = mutableListOf()
            data.add(quotesListEmptyItem)
            data.addAll(it)
            adapter.addData(data)
            recyclerView.adapter?.notifyDataSetChanged()
        }

        observers()

    }

    private fun initViews(view: View) {
        errorView = view.findViewById(R.id.random_error_view)
        progressBar = view.findViewById(R.id.random_progress_bar)
        errorTV = view.findViewById(R.id.random_error_message_tv)
        retryButton = view.findViewById(R.id.random_retry_btn)
    }



}