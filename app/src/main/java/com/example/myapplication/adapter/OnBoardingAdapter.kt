package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class OnBoardingAdapter(val pageLists: List<OnBoardingViewItem>) :
    RecyclerView.Adapter<OnBoardingViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.on_boarding_item, parent, false)
        return OnBoardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(pageLists[position])
    }

    override fun getItemCount() = pageLists.size
}