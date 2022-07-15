package com.example.animechan.search.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.animechan.R
import com.example.animechan.random.model.Quote
import com.example.animechan.random.ui.QuotesRecyclerViewVH


class SearchRecyclerAdapter(private val quotes: MutableList<Quote>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM = 0
        private const val LOADING = 1
    }

    var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == LOADING) {
            val inflater =
                LayoutInflater.from(parent.context).inflate(R.layout.loading_item, parent, false)
            return QuotesLoadingViewHolder(inflater)
        }
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_quote_item, parent, false)
        return QuotesRecyclerViewVH(inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == LOADING) {
            holder as QuotesLoadingViewHolder
            holder.progressBar.visibility = View.VISIBLE
        } else {
            holder as QuotesRecyclerViewVH
            holder.bind(quotes[position])
        }
    }

    override fun getItemCount(): Int = quotes.size

    fun clearData() {
        quotes.clear()
        notifyDataSetChanged()
    }

    fun addData(data: List<Quote>) {
        quotes.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == quotes.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun addLoadingFooter(recyclerView: RecyclerView) {
        isLoadingAdded = true
        quotes.add(Quote("", "", ""))
        recyclerView.post {
            notifyItemInserted(quotes.size - 1)
        }
    }

    fun removeLoadingFooter(recyclerView: RecyclerView) {
        isLoadingAdded = false
        val position: Int = quotes.size - 1
        if (quotes.isNotEmpty()) quotes.removeAt(position)
        recyclerView.post {
            notifyItemRemoved(position)
        }
    }

}

class QuotesLoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var progressBar: ProgressBar

    init {
        progressBar = itemView.findViewById(R.id.recycler_loading_pb)
    }
}
