package com.example.androidmoviesproject.adapter.categoryMovie

import android.widget.TextView
import coil.load
import com.example.androidmoviesproject.adapter.base.ItemClicked
import com.example.androidmoviesproject.adapter.base.ViewHolderBase
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.databinding.MovieCategoryItemBinding
import com.google.android.material.imageview.ShapeableImageView

class CategoryMovieHolder(binding: MovieCategoryItemBinding) :
    ViewHolderBase<ModelMovie>(binding.root) {
    private val imageMovie: ShapeableImageView = binding.imageMovie
    private val nameMovie: TextView = binding.nameMovie
    override fun displayData(value: ModelMovie, itemClicked: ItemClicked) {
        val movie: ModelMovie = value
        /** Use library coil to load image from internet */
        imageMovie.load("https://image.tmdb.org/t/p/original/" + movie.backDropPath) {
            crossfade(
                true
            )
        }
        nameMovie.text = movie.title
        itemView.setOnClickListener {
            itemClicked.onClick(value)
        }
    }
}