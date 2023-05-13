package com.example.androidmoviesproject.adapter.trending

import android.widget.TextView
import coil.load
import com.example.androidmoviesproject.adapter.base.ItemClicked
import com.example.androidmoviesproject.adapter.base.ViewHolderBase
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.databinding.TrendingItemBinding
import com.google.android.material.imageview.ShapeableImageView

class TrendingHolder(binding: TrendingItemBinding) : ViewHolderBase<ModelMovie>(binding.root) {
    private val imageMovie: ShapeableImageView = binding.imageMovie
    private val nameMovie: TextView = binding.nameMovie
    private val productMovie: TextView = binding.productMovie
    private val watchedMovie: TextView = binding.watchedMovie
    override fun displayData(value: ModelMovie, itemClicked: ItemClicked) {
        nameMovie.text = value.title
        watchedMovie.text = value.popularity.toString()
        productMovie.text = value.releaseDate
        imageMovie.load("https://image.tmdb.org/t/p/original/" + value.backDropPath) {
            crossfade(true)
        }
        itemView.setOnClickListener {
            itemClicked.onClick(value)
        }
    }
}