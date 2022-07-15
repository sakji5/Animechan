package com.example.animechan.search.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animechan.R
import com.example.animechan.utils.BaseFragment
import com.example.animechan.utils.PaginationScrollListener
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.snackbar.Snackbar

class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private lateinit var adapter: SearchRecyclerAdapter

    private lateinit var toggleButtonGroup: MaterialButtonToggleGroup
    private lateinit var characterBtn: Button
    private lateinit var searchBtn: Button
    private lateinit var queryEditText: EditText
    private lateinit var animeBtn: Button

    private lateinit var paginationListener: PaginationListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        val searchViewModel = viewModel as SearchViewModel

        adapter = SearchRecyclerAdapter(mutableListOf())

        initViews(view)
        observers()
        initRecyclerView(view, R.id.search_recycler_view, adapter, true)

        paginationListener = PaginationListener(
            this,
            linearLayoutManager,
            false
        ) { adapter.addLoadingFooter() }

         recyclerView.addOnScrollListener(paginationListener)

        searchBtn.setOnClickListener {
            adapter.clearData()
            val query: String = queryEditText.text.toString()
            searchViewModel.getQuotes(query, toggleButtonGroup.checkedButtonIds)
        }

        searchViewModel.quotes.observe(viewLifecycleOwner) {
            adapter.removeLoadingFooter()
            adapter.addData(it)
            adapter.notifyDataSetChanged()
        }

        searchViewModel.dataValidatorMessageInfo.observe(viewLifecycleOwner) { errorMessage ->
            this.view?.let {
                if (errorMessage != null) {
                    Snackbar.make(it, errorMessage, Snackbar.LENGTH_LONG)
                        .setBackgroundTint(Color.RED).show()
                }
            }
        }
    }

    private fun initViews(view: View) {
        errorView = view.findViewById(R.id.search_error_view)
        progressBar = view.findViewById(R.id.search_progress_bar)
        errorTV = view.findViewById(R.id.search_error_message_tv)
        retryButton = view.findViewById(R.id.search_retry_btn)

        toggleButtonGroup = view.findViewById(R.id.search_parameter_toggle_btn)
        animeBtn = view.findViewById(R.id.anime_title_btn)
        characterBtn = view.findViewById(R.id.character_name_btn)
        searchBtn = view.findViewById(R.id.search_button)
        queryEditText = view.findViewById(R.id.edit_query)

        toggleButtonGroup.isSingleSelection = true

    }

}

class PaginationListener(
    private val viewModelStoreOwner: ViewModelStoreOwner,
    layoutManager: LinearLayoutManager,
    override var isLoading: Boolean,
    val addLoadingFooter: () -> Unit

) : PaginationScrollListener(layoutManager) {

    override fun loadMoreItems() {

        val viewModel = ViewModelProvider(viewModelStoreOwner)[SearchViewModel::class.java]
        isLoading = true
        viewModel.loadNextPage()
        addLoadingFooter()

    }
}