package com.example.animechan.random.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animechan.R
import com.example.animechan.random.model.RandomQuote

class RandomRecyclerViewAdapter(
    private val quotes: MutableList<RandomQuote>,
    private val onBtnClick: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layout =
            if (viewType == 0) R.layout.recycler_header_random else R.layout.recycler_random_quote_item
        val inflater = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)
        return if (viewType == 0)
            RandomRecyclerHeaderVH(inflater)
        else
            RandomRecyclerViewVH(inflater)
    }


    override fun getItemCount(): Int = quotes.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == 0) {
            (holder as RandomRecyclerHeaderVH).getQuotesBtn.setOnClickListener { onBtnClick () ; clearData() }
        } else {
            (holder as RandomRecyclerViewVH).animeTV.text = quotes[position].anime
            holder.characterTV.text = quotes[position].character
            val quoteText = "\"${quotes[position].quote}\" "
            holder.quoteTV.text = quoteText
        }
    }

    private fun clearData(){
        quotes.clear()
        quotes.add(RandomQuote("", "", ""))
        notifyDataSetChanged()
    }


    fun addData(data: List<RandomQuote>) {
        quotes.clear()
        quotes.addAll(data)
        notifyDataSetChanged()
    }

}

class RandomRecyclerViewVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var animeTV: TextView
    var characterTV: TextView
    var quoteTV: TextView

    init {
        animeTV = itemView.findViewById(R.id.anime_tv)
        characterTV = itemView.findViewById(R.id.character_tv)
        quoteTV = itemView.findViewById(R.id.quote_tv)
    }
}

class RandomRecyclerHeaderVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var getQuotesBtn: Button

    init {
        getQuotesBtn = itemView.findViewById(R.id.get_quotes_btn)
    }

}