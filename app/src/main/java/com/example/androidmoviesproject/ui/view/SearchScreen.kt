package com.example.androidmoviesproject.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.adapter.base.ItemClicked
import com.example.androidmoviesproject.adapter.search.SearchAdapter
import com.example.androidmoviesproject.broadcast.NetworkStatus
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.databinding.FragmentSearchScreenBinding
import com.example.androidmoviesproject.ui.viewmodel.SearchViewModel
import com.example.androidmoviesproject.utils.Constants
import com.example.androidmoviesproject.utils.Constants.DISCONNECT_NETWORK
import com.example.androidmoviesproject.utils.Constants.NETWORK_STATUS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class SearchScreen : Fragment(), ItemClicked {
    private lateinit var binding: FragmentSearchScreenBinding
    private val viewModel: SearchViewModel by viewModels()
    private val adapterRecommend = SearchAdapter(this)
    private val adapterSearch = SearchAdapter(this)

    @Inject
    @Named(NETWORK_STATUS)
    lateinit var networkStatus: NetworkStatus

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
                loadData(invoke = {
                    viewModel.getSearchData(search = search, adapterSearch.pageIncrease())
                })
            } else {
                binding.title.visibility = View.VISIBLE
                binding.recycleSearch.adapter = adapterRecommend
            }
        }
        loadData(invoke = {
            viewModel.getRecommendData(page = 1)
        })
    }

    private fun setUpRecycleView() {
        lifecycleScope.launch {
            viewModel.searchData().collect { listMovie ->
                adapterSearch.submitList(listMovie)
                binding.recycleSearch.adapter = adapterSearch
            }
        }
        lifecycleScope.launch {
            viewModel.recommendData().collect { listMovie ->
                adapterRecommend.submitList(listMovie)
                binding.recycleSearch.adapter = adapterRecommend
            }
        }
        binding.recycleSearch.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recycleSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.recycleSearch.canScrollVertically(1)) {
                    if (binding.recycleSearch.adapter == adapterRecommend) {
                        val page = adapterRecommend.pageIncrease()
                        if (page != -1 && page != 2)
                            loadData(invoke = {
                                viewModel.getRecommendData(page = page)
                            })
                        else
                            if (page == -1)
                                binding.recycleSearch.scrollToPosition(0)
                    } else {
                        val search = binding.edtSearch.text.toString()
                        if (!search.isNullOrEmpty()) {
                            val page = adapterSearch.pageIncrease()
                            if (page != -1 && page != 2)
                                loadData(invoke = {
                                    viewModel.getSearchData(search = search, page = page)
                                })
                            else
                                if (page == -1)
                                    binding.recycleSearch.scrollToPosition(0)
                        }
                    }
                }
            }
        })
    }

    private fun setUpToolBar() {
        binding.backNavigation.setOnClickListener {
            findNavController().navigate(R.id.action_findMoveScreen_to_homeFragment)
        }
    }

    private inline fun loadData(crossinline invoke: () -> Unit = {}) {
        if (networkStatus.isOnline()) {
            invoke.invoke()
        } else {
            Toast.makeText(requireContext(), "$DISCONNECT_NETWORK", Toast.LENGTH_SHORT).show()
            networkStatus.networkState().observe(viewLifecycleOwner) { value: Boolean ->
                if (value) {
                    invoke.invoke()
                    networkStatus.networkState().removeObservers(viewLifecycleOwner)
                }
            }
        }
    }


    override fun onClick(value: ModelMovie?, imageView: ImageView?) {
        if (value != null) if (value.id != null) {
            val destination =
                SearchScreenDirections.actionFindMoveScreenToDetailScreen(value = value.id)
            findNavController().navigate(destination)
        }
    }
}