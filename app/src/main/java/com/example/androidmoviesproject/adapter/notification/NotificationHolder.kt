package com.example.androidmoviesproject.adapter.notification

import androidx.recyclerview.widget.RecyclerView
import com.example.androidmoviesproject.data.model.ModelNotification
import com.example.androidmoviesproject.databinding.NotificationItemBinding

class NotificationHolder(binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val image = binding.image
    private val title = binding.title
    fun display(value: ModelNotification) {
        title.text = value.title
    }
}