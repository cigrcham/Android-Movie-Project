package com.example.androidmoviesproject.adapter.category

import androidx.recyclerview.widget.RecyclerView
import com.example.androidmoviesproject.databinding.CategoryItemBinding

class CategoryHolder(binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val iconStatus = binding.iconStatus
    val titleCategory = binding.titleCategory
    val categoryContainer = binding.categoryContainer
    fun bind(click: () -> Unit) {
        itemView.setOnClickListener {
            click.invoke()
        }
    }
}