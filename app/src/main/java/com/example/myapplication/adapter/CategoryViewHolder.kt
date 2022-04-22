package com.example.myapplication.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R

class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val imageView = view.findViewById<ImageView>(R.id.iv_category)
    private val textView = view.findViewById<TextView>(R.id.tv_category)
    private val context = view.context

    fun bind(categoryViewItem: CategoryOnlineViewItem) {
        Glide.with(context).load(categoryViewItem.Image).into(imageView)
        textView.text = categoryViewItem.Type
    }
}