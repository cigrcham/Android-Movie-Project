package com.example.androidmoviesproject.adapter.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * T is Model Data when display in ViewHolderItem
 * */
abstract class ViewHolderBase<T : Any>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun displayData(value: T, itemClicked: ItemClicked)
}