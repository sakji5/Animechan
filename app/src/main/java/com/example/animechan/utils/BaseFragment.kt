package com.example.animechan.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animechan.R
import com.example.animechan.home.ui.HomeRecyclerViewAdapter
import com.example.animechan.random.model.Quote
import com.example.animechan.random.ui.RandomRecyclerViewAdapter

open class BaseFragment (private val layoutId: Int) : Fragment() {

    protected lateinit var viewModel: BaseViewModel

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var errorTV: TextView
    lateinit var errorView: LinearLayout
    lateinit var retryButton: Button

    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(layoutId, container, false)
    }

    fun observers() {
        viewModel.progressBarState.observe(viewLifecycleOwner) { progressBar.visibility = it }

        viewModel.errorMessageState.observe(viewLifecycleOwner) {
            errorView.visibility = it.viewVisibility
            errorTV.text = it.errorMessage
        }
    }

    fun initRecyclerView(
        view: View,
        id: Int,
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
        showDivider: Boolean = false
    ) {
        recyclerView = view.findViewById(id)
        recyclerView.adapter = adapter
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        if (showDivider)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )

    }
}