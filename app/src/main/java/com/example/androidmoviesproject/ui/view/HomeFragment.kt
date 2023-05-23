package com.example.androidmoviesproject.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.adapter.base.AdapterBase
import com.example.androidmoviesproject.adapter.base.ItemClicked
import com.example.androidmoviesproject.adapter.category.CategoryAdapter
import com.example.androidmoviesproject.adapter.categoryMovie.CategoryMovieAdapter
import com.example.androidmoviesproject.adapter.foryou.ForYouAdapter
import com.example.androidmoviesproject.adapter.trending.TrendingAdapter
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.databinding.FragmentHomeBinding
import com.example.androidmoviesproject.ui.viewmodel.HomeViewModel
import com.example.androidmoviesproject.utils.StateListResult
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), ItemClicked, OnNavigationItemSelectedListener {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTrending()
        setUpForYou()
        setUpCategory()
        searchMovieClick()
        notificationClick()
        setUpDrawer()
    }

    /** Set Recycler View */
    private fun setUpForYou() {
        val adapter = ForYouAdapter(this)
        binding.forYouRecycle.adapter = adapter
        binding.forYouRecycle.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
        lifecycleScope.launch {
            viewModel.forYouData().collect { listMovies ->
                if (listMovies != null)
                    when (listMovies) {
                        is StateListResult.Success<*> -> {
                            Log.d("HomeFragment", "setUpForYou: have list")
                            adapter.submitList(listMovies.value as List<ModelMovie>)
                        }

                        is StateListResult.Error -> {
                            Log.d("HomeFragment", "setUpForYou: not have list")
                            Toast.makeText(
                                requireContext(),
                                "${listMovies.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
        binding.forYouRecycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.forYouRecycle.canScrollHorizontally(1)) {
                    val page = adapter.pageIncrease()
                    if (page == -1) binding.forYouRecycle.scrollToPosition(0)
                    else viewModel.getForYouData(page)
                }
            }
        })
        binding.txtForYou.setOnClickListener {
            binding.forYouRecycle.scrollToPosition(0)
        }
    }

    private fun setUpTrending() {
        val adapter = TrendingAdapter(this)
        binding.trendingRecycleView.adapter = adapter
        binding.trendingRecycleView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        lifecycleScope.launch {
            viewModel.trendingData().collect { listMovies ->
                if (listMovies != null)
                    when (listMovies) {
                        is StateListResult.Success<*> ->
                            adapter.submitList(listMovies.value as List<ModelMovie>)

                        is StateListResult.Error ->
                            Toast.makeText(
                                requireContext(),
                                "${listMovies.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
            }
        }

        binding.trendingRecycleView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!binding.trendingRecycleView.canScrollHorizontally(1)) {
                        val page = adapter.pageIncrease()
                        if (page == -1) binding.trendingRecycleView.scrollToPosition(0)
                        else viewModel.getTrendingData(page)
                    }
                }
            })

        binding.txtTrending.setOnClickListener {
            binding.trendingRecycleView.scrollToPosition(0)
        }
    }

    private fun setUpCategory() {
        val adapter = CategoryAdapter()
        binding.categoryRecycler.adapter = adapter
        binding.categoryRecycler.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
        binding.categoryListRecycler.layoutManager = GridLayoutManager(context, 2)
        val nowPlayingAdapter = CategoryMovieAdapter(this)
        val popularAdapter = CategoryMovieAdapter(this)
        val upComingAdapter = CategoryMovieAdapter(this)

        // Receive from flow in viewModel
        lifecycleScope.launch {
            viewModel.nowPlayingMovie().collect { listMovie ->
                if (listMovie != null)
                    when (listMovie) {
                        is StateListResult.Success<*> ->
                            nowPlayingAdapter.submitList(listMovie.value as List<ModelMovie>)

                        is StateListResult.Error ->
                            Toast.makeText(
                                requireContext(),
                                "${listMovie.message as String}",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
            }
        }
        lifecycleScope.launch {
            viewModel.popularMovie().collect { listMovie ->
                if (listMovie != null)
                    when (listMovie) {
                        is StateListResult.Success<*> ->
                            popularAdapter.submitList(listMovie.value as List<ModelMovie>)

                        is StateListResult.Error ->
                            Toast.makeText(
                                requireContext(),
                                "${listMovie.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
            }
        }
        lifecycleScope.launch {
            viewModel.upComingMovie().collect { listMovie ->
                if (listMovie != null)
                    when (listMovie) {
                        is StateListResult.Success<*> ->
                            upComingAdapter.submitList(listMovie.value as List<ModelMovie>)

                        is StateListResult.Error ->
                            Toast.makeText(
                                requireContext(),
                                "${listMovie.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                    }
            }
        }

        adapter.currentIndex().observe(viewLifecycleOwner) {
            binding.categoryListRecycler.adapter = when (it) {
                0 -> {
                    if (nowPlayingAdapter.pageIncrease() == 1) viewModel.getNowPlayingData(
                        nowPlayingAdapter.pageIncrease()
                    )
                    nowPlayingAdapter
                }

                1 -> {
                    if (popularAdapter.pageIncrease() == 1) viewModel.getPopularMovieData(
                        popularAdapter.pageIncrease()
                    )
                    popularAdapter
                }

                2 -> {

                    if (upComingAdapter.pageIncrease() == 1) viewModel.getUpComingData(
                        upComingAdapter.pageIncrease()
                    )
                    upComingAdapter
                }

                else -> {
                    nowPlayingAdapter
                }
            }
        }

        binding.categoryListRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.categoryListRecycler.canScrollVertically(1)) {
                    if (adapter.currentIndex().value == 0) {
                        val page = nowPlayingAdapter.pageIncrease()
                        if (page == -1) binding.categoryListRecycler.scrollToPosition(0)
                        else viewModel.getNowPlayingData(page)
                    } else if (adapter.currentIndex().value == 1) {
                        val page = popularAdapter.pageIncrease()
                        if (page == -1) binding.categoryListRecycler.scrollToPosition(0)
                        else viewModel.getPopularMovieData(page)
                    } else {
                        val page = upComingAdapter.pageIncrease()
                        if (page == -1) binding.categoryListRecycler.scrollToPosition(0)
                        else viewModel.getUpComingData(page)
                    }

                }
            }
        })
    }

    /** Icon Clicked */
    private fun searchMovieClick() = binding.iconSearch.setOnClickListener {
        findNavController().navigate(R.id.action_homeFragment_to_findMoveScreen)
    }

    private fun notificationClick() = binding.iconNotification.setOnClickListener {
        findNavController().navigate(R.id.action_homeFragment_to_notificationScreen)
    }

    /** When click item in recycle view*/
    override fun onClick(value: ModelMovie?) {
        if (value != null) {
            if (value.id != null) {
                val destination = HomeFragmentDirections.actionHomeFragmentToDetailScreen(value.id)
                findNavController().navigate(destination)
            }
        }
    }

    /** Set up Drawer Layout with Menu and Item Clicked */
    private fun setUpDrawer() {
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            requireActivity(), binding.drawerLayout, R.string.open, R.string.close
        )
        actionBarDrawerToggle.syncState()
        binding.profile.setOnClickListener {
            binding.drawerLayout.open()
        }
        binding.navView.setNavigationItemSelectedListener(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.drawer_menu, menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_menu -> {
                findNavController().navigate(R.id.action_homeFragment_to_findMoveScreen)
                true
            }

            R.id.logout_menu -> {
                viewModel.signOutAccount()
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                true
            }

            R.id.notification_menu -> {
                findNavController().navigate(R.id.action_homeFragment_to_notificationScreen)
                true
            }

            R.id.profile_menu -> {
                true
            }

            R.id.message_menu -> {
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}