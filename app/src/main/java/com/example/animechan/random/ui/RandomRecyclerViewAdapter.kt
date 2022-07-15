package com.example.animechan.random.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animechan.R
import com.example.animechan.random.model.Quote

class RandomRecyclerViewAdapter(
    private val quotes: MutableList<Quote>,
    private val onBtnClick: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout =
            if (viewType == 0) R.layout.recycler_header_random else R.layout.recycler_quote_item
        val inflater = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)
        return if (viewType == 0)
            RandomRecyclerHeaderVH(inflater)
        else
            QuotesRecyclerViewVH(inflater)
    }


    override fun getItemCount(): Int = quotes.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == 0) {
            (holder as RandomRecyclerHeaderVH).getQuotesBtn.setOnClickListener { onBtnClick(); clearData() }
        } else {
            (holder as QuotesRecyclerViewVH)
            holder.bind(quotes[position])
        }
    }

    private fun clearData() {
        quotes.clear()
        quotes.add(Quote("", "", ""))
        notifyDataSetChanged()
    }


    fun addData(data: List<Quote>) {
        quotes.clear()
        quotes.addAll(data)
        notifyDataSetChanged()
    }

}

class QuotesRecyclerViewVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var animeTV: TextView
    var characterTV: TextView
    var quoteTV: TextView

    init {
        animeTV = itemView.findViewById(R.id.anime_tv)
        characterTV = itemView.findViewById(R.id.character_tv)
        quoteTV = itemView.findViewById(R.id.quote_tv)
    }

    fun bind(item: Quote) {
        animeTV.text = item.anime
        characterTV.text = item.character
        quoteTV.text = item.quote
    }
}

class RandomRecyclerHeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var getQuotesBtn: Button

    init {
        getQuotesBtn = itemView.findViewById(R.id.get_quotes_btn)
    }

}