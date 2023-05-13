package com.example.androidmoviesproject.adapter.base

import androidx.recyclerview.widget.RecyclerView

/**
 * T is Model Data when display in ViewHolder
 * */
abstract class AdapterBase<T : Any>(private val itemClicked: ItemClicked) :
    RecyclerView.Adapter<ViewHolderBase<T>>() {
    /**
     * Save List
     * */
    private val lists: MutableList<T> = mutableListOf()
    private var page = 0
    fun pageIncrease(): Int {
        if (page + 1 > 1000) return -1
        return ++page
    }

    private fun submitItem(value: T) {
        lists.add(value)
    }

    protected fun cleanList() {
        page = 0
        lists.clear()
    }

    fun submitList(value: List<T>) {
        value.forEach {
            submitItem(it)
        }
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    override fun onBindViewHolder(holder: ViewHolderBase<T>, position: Int) {
        holder.displayData(lists[position], itemClicked)
    }
}