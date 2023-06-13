package com.example.androidmoviesproject.adapter.actorDetail

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidmoviesproject.data.model.actorMovie.Cast
import com.example.androidmoviesproject.databinding.ActorItemBinding

class ActorsHolder(binding: ActorItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val imageActor = binding.imageActor
    private val nameActor = binding.nameActor
    fun display(value: Cast) {
        if (!value.profilePath.isNullOrBlank() && value.profilePath != "null" && value.profilePath.isNotEmpty()) {
            nameActor.text = value.name
            imageActor.load("https://image.tmdb.org/t/p/original/" + value.profilePath) {
                crossfade(true)
            }
        }
    }
}