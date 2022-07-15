package com.example.animechan.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.animechan.R

class HomeRecyclerViewAdapter(private val animeList: MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewVH {

        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_anime, parent, false)
        return HomeRecyclerViewVH(inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeRecyclerViewVH)
        holder.itemTextView.textSize = 16.0F
        if (isHeader(position)){
            holder.itemTextView.text = holder.itemView.context.getString(R.string.available_anime)
            holder.itemTextView.textSize = 28.0F
        }
        else
            holder.itemTextView.text = animeList[position - 1]
    }

    override fun getItemCount(): Int = animeList.size + 1

    fun addData(data: List<String>) {
        animeList.clear()
        animeList.addAll(data)
    }

    private fun isHeader(position: Int) = position == 0
}

class HomeRecyclerViewVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var itemTextView: TextView

    init {
        itemTextView = itemView.findViewById(R.id.anime_title_tv)
    }
}
