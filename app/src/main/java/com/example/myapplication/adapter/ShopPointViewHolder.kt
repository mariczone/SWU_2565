package com.example.myapplication.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ShopcardTemplateBinding

class ShopPointViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ShopcardTemplateBinding.bind(view)

    fun bind(item: ShopPointViewItem) {
        binding.tvShopTitle.text = item.title
        binding.tvShopDescr.text = item.description
        binding.ivShopImg.setImageResource(item.imageId)
    }
}