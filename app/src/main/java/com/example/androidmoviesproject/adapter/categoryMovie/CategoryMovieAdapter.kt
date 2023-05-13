package com.example.androidmoviesproject.adapter.categoryMovie

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.androidmoviesproject.adapter.base.AdapterBase
import com.example.androidmoviesproject.adapter.base.ItemClicked
import com.example.androidmoviesproject.adapter.base.ViewHolderBase
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.databinding.MovieCategoryItemBinding

/**
 * Adapter for Recycle View list movie in HomeScreen
 * */
class CategoryMovieAdapter(itemClicked: ItemClicked) :
    AdapterBase<ModelMovie>(itemClicked = itemClicked) {
    private lateinit var binding: MovieCategoryItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderBase<ModelMovie> {
        binding =
            MovieCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryMovieHolder(binding = binding)
    }
}