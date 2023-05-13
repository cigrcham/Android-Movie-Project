package com.example.androidmoviesproject.adapter.notification

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmoviesproject.data.model.ModelNotification
import com.example.androidmoviesproject.databinding.NotificationItemBinding

/**
 * Adapter for RecycleVIew in Notification Screen
 * */
class NotificationAdapter : RecyclerView.Adapter<NotificationHolder>() {
    private val TAG: String = this::class.java.simpleName
    private val listsNotification: MutableList<ModelNotification> = mutableListOf()
    private lateinit var binding: NotificationItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        binding =
            NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationHolder(binding = binding)
    }

    private fun addItem(value: ModelNotification) {
        listsNotification.add(value)
    }

    fun submitList(lists: List<ModelNotification>) {
        listsNotification.clear()
        lists.forEach {
            addItem(it)
        }
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listsNotification.size
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.display(listsNotification[position])
    }
}