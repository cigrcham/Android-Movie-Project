package com.example.androidmoviesproject.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.adapter.base.ItemClicked
import com.example.androidmoviesproject.adapter.search.SearchAdapter
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.databinding.FragmentSearchScreenBinding
import com.example.androidmoviesproject.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchScreen : Fragment(), ItemClicked {
    private lateinit var binding: FragmentSearchScreenBinding
    private val viewModel: SearchViewModel by viewModels()
    private val adapterRecommend = SearchAdapter(this)
    private val adapterSearch = SearchAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolBar()
        setUpRecycleView()

        binding.edtSearch.addTextChangedListener {
            val search: String = binding.edtSearch.text.toString()
            if (search.isNotEmpty()) {
                binding.title.visibility = View.GONE
                adapterSearch.clearList()
                viewModel.getSearchData(search = search, page = adapterSearch.pageIncrease())
            } else {
                binding.title.visibility = View.VISIBLE
                binding.recycleSearch.adapter = adapterRecommend
            }
        }
    }

    private fun setUpRecycleView() {
        lifecycleScope.launch {
            viewModel.searchData().collect { listMovie ->
                if (listMovie != null) {
                    adapterSearch.submitList(listMovie)
                    binding.recycleSearch.adapter = adapterSearch
                }
            }
        }
        lifecycleScope.launch {
            viewModel.recommendData().collect { listMovie ->
                if (listMovie != null) {
                    adapterRecommend.submitList(listMovie) {
//                        loadingSpinner(false)
                    }
                }
            }
        }
        viewModel.getRecommendData(adapterRecommend.pageIncrease())
        binding.recycleSearch.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recycleSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.recycleSearch.canScrollVertically(1)) {
                    if (binding.recycleSearch.adapter == adapterRecommend) {
                        val page = adapterRecommend.pageIncrease()
                        if (page == -1) binding.recycleSearch.scrollToPosition(0)
                        else {
//                            loadingSpinner(check = true)
                            viewModel.getRecommendData(page)
                        }
                    } else {
                        val search = binding.edtSearch.text.toString()
                        if (!search.isNullOrEmpty()) {
                            val page = adapterSearch.pageIncrease()
                            if (page == -1) binding.recycleSearch.scrollToPosition(0)
                            else {
//                                loadingSpinner(check = true)
                                viewModel.getSearchData(search, page)
                            }
                        }
                    }
                }
            }
        })
        binding.recycleSearch.adapter = adapterRecommend
    }

    private fun setUpToolBar() {
        binding.backNavigation.setOnClickListener {
            findNavController().navigate(R.id.action_findMoveScreen_to_homeFragment)
        }
    }

    override fun onClick(value: ModelMovie?) {
        if (value != null) if (value.id != null) {
            val destination =
                SearchScreenDirections.actionFindMoveScreenToDetailScreen(value = value.id)
            findNavController().navigate(destination)
        }
    }

//    private fun loadingSpinner(check: Boolean) {
//        val shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
//        if (check) {
//            binding.content.animate().alpha(0f)
//                .setDuration(shortAnimationDuration.toLong())
//                .setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator) {
//                        super.onAnimationEnd(animation)
//                        binding.content.visibility = View.INVISIBLE
//                    }
//                })
//            binding.loadingSpinner.animate().alpha(1f)
//                .setDuration(shortAnimationDuration.toLong())
//                .setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator) {
//                        super.onAnimationEnd(animation)
//                        binding.loadingSpinner.visibility = View.VISIBLE
//                    }
//                })
//        } else {
//            binding.content.animate().alpha(1f)
//                .setDuration(shortAnimationDuration.toLong())
//                .setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator) {
//                        super.onAnimationEnd(animation)
//                        binding.content.visibility = View.VISIBLE
//                    }
//                })
//            binding.loadingSpinner.animate().alpha(0f)
//                .setDuration(shortAnimationDuration.toLong())
//                .setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator) {
//                        super.onAnimationEnd(animation)
//                        binding.loadingSpinner.visibility = View.GONE
//                    }
//                })
//        }
//    }
}