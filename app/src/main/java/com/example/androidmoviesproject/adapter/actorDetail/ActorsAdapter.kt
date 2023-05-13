package com.example.androidmoviesproject.adapter.actorDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmoviesproject.data.model.actorMovie.Cast
import com.example.androidmoviesproject.databinding.ActorItemBinding

/**
 * Display Recycle View in HomeScreen
 * */
class ActorsAdapter : RecyclerView.Adapter<ActorsHolder>() {
    /**
     * Save list Caster
     * */
    private val listCasts: MutableList<Cast> = mutableListOf()
    private lateinit var binding: ActorItemBinding
    private fun addItem(value: Cast) {
        if (value.profilePath != null && !value.profilePath.isNullOrEmpty()) listCasts.add(value)
    }

    fun submitList(lists: List<Cast?>) {
        lists.forEach {
            if (it != null) {
                addItem(it)
            }
        }
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsHolder {
        binding = ActorItemBinding.inflate((LayoutInflater.from(parent.context)), parent, false)
        return ActorsHolder(binding = binding)
    }

    override fun getItemCount(): Int {
        return listCasts.size
    }

    override fun onBindViewHolder(holder: ActorsHolder, position: Int) {
        holder.display(listCasts[position])
    }
}