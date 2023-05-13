package com.example.androidmoviesproject.adapter.foryou

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.androidmoviesproject.adapter.base.AdapterBase
import com.example.androidmoviesproject.adapter.base.ItemClicked
import com.example.androidmoviesproject.adapter.base.ViewHolderBase
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.databinding.ForYouItemBinding

/**
 * Adapter for RecycleView of For You HomeScreen
 * */
class ForYouAdapter(itemClicked: ItemClicked) : AdapterBase<ModelMovie>(itemClicked = itemClicked) {
    private lateinit var binding: ForYouItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBase<ModelMovie> {
        binding = ForYouItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForYouHolder(binding = binding)
    }
}