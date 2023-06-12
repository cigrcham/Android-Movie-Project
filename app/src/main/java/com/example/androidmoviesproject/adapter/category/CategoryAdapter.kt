package com.example.androidmoviesproject.adapter.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.databinding.CategoryItemBinding
import com.example.androidmoviesproject.utils.Constants.CATEGORY_MOVIE
import javax.inject.Inject

/**
 * Adapter for Category RecycleView in HomeScreen
 * */
class CategoryAdapter : RecyclerView.Adapter<CategoryHolder>() {
    /**
     * Save State Of Category in Home Screen
     * */
    private var _currentIndex: MutableLiveData<Int> = MutableLiveData(0)
    fun currentIndex() = _currentIndex

    /**
     * List Category
     * */
    private val categoryLists: List<Int> = CATEGORY_MOVIE
    private lateinit var binding: CategoryItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        binding = CategoryItemBinding.inflate((LayoutInflater.from(parent.context)), parent, false)
        return CategoryHolder(binding = binding)
    }

    override fun getItemCount(): Int {
        return categoryLists.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        itemChoose(holder, position == _currentIndex.value)
        binding.titleCategory.setText(categoryLists[position])
        var currentPosition: Int = position
        holder.bind {
            _currentIndex.value = currentPosition
            this.notifyDataSetChanged()
        }
    }

    private fun itemChoose(holder: CategoryHolder, choose: Boolean = false) {
        if (choose) {
            holder.iconStatus.visibility = View.VISIBLE
            holder.categoryContainer.setBackgroundResource(R.drawable.category_layout)
        } else {
            holder.iconStatus.visibility = View.GONE
            holder.categoryContainer.background = null
        }
    }
}