package com.example.myapplication.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.OnBoardingItemBinding

class OnBoardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = OnBoardingItemBinding.bind(view)

    fun bind(onBoardingViewItem: OnBoardingViewItem) {
        binding.imageView.setImageResource(onBoardingViewItem.imageId)
        binding.title.text = onBoardingViewItem.title
        binding.description.text = onBoardingViewItem.description
    }
}