package com.example.quizy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizy.R
import com.example.quizy.models.News

class NewsAdapter(private val listener :NewsItemClicked): RecyclerView.Adapter<newsViewsHolder>() {
    private val items:ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsViewsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val viewHolder= newsViewsHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
       return items.size
    }

    override fun onBindViewHolder(holder: newsViewsHolder, position: Int) {
        val currentItem=items[position]
        holder.titleView.text=currentItem.title
    }

    fun updateNews(updateNews:ArrayList<News>){
        items.clear()
        items.addAll(updateNews)
        notifyDataSetChanged()
    }
}
class newsViewsHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
    val titleView :TextView = itemView.findViewById(R.id.title)
}

interface NewsItemClicked{
    fun onItemClicked(item:News)
}