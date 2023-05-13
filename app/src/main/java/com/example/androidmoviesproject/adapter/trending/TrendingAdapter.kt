package com.example.androidmoviesproject.adapter.trending

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.androidmoviesproject.adapter.base.AdapterBase
import com.example.androidmoviesproject.adapter.base.ItemClicked
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.databinding.TrendingItemBinding

/**
 * Adapter for RecycleView of Trending in HomeScreen
 * */
class TrendingAdapter(itemClicked: ItemClicked) :
    AdapterBase<ModelMovie>(itemClicked = itemClicked) {
    private lateinit var binding: TrendingItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingHolder {
        binding = TrendingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingHolder(binding = binding)
    }
}
