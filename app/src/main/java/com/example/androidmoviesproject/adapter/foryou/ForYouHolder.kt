package com.example.androidmoviesproject.adapter.foryou

import android.widget.TextView
import coil.load
import com.example.androidmoviesproject.adapter.base.ItemClicked
import com.example.androidmoviesproject.adapter.base.ViewHolderBase
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.databinding.ForYouItemBinding
import com.google.android.material.imageview.ShapeableImageView

class ForYouHolder(val binding: ForYouItemBinding) : ViewHolderBase<ModelMovie>(binding.root) {
    private val nameMovie: TextView = binding.nameMovie
    private val yearMovie: TextView = binding.yearMovie
    private val imageMovie: ShapeableImageView = binding.imageMovie
    override fun displayData(value: ModelMovie, itemClicked: ItemClicked) {
        val movie: ModelMovie = value
        nameMovie.text = movie.title
        yearMovie.text = movie.releaseDate
        imageMovie.load("https://image.tmdb.org/t/p/original/" + movie.backDropPath) {
            crossfade(true)
        }
        itemView.setOnClickListener {
            itemClicked.onClick(value, binding.imageMovie)
        }
    }
}