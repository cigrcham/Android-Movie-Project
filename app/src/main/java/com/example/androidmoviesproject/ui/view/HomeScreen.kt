package com.example.androidmoviesproject.ui.view

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.adapter.base.ItemClicked
import com.example.androidmoviesproject.adapter.category.CategoryAdapter
import com.example.androidmoviesproject.adapter.categoryMovie.CategoryMovieAdapter
import com.example.androidmoviesproject.adapter.foryou.ForYouAdapter
import com.example.androidmoviesproject.adapter.trending.TrendingAdapter
import com.example.androidmoviesproject.broadcast.NetworkStatus
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.databinding.FragmentHomeBinding
import com.example.androidmoviesproject.ui.ContainerActivity
import com.example.androidmoviesproject.ui.viewmodel.HomeViewModel
import com.example.androidmoviesproject.utils.Constants.NETWORK_STATUS
import com.example.androidmoviesproject.utils.StateListResult
import com.facebook.shimmer.Shimmer
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class HomeScreen : Fragment(R.layout.fragment_home), ItemClicked, OnNavigationItemSelectedListener {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var shimmerBuilder: Shimmer

    @Inject
    @Named(NETWORK_STATUS)
    lateinit var networkStatus: NetworkStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create Shimmer Builder
        shimmerBuilder =
            Shimmer.AlphaHighlightBuilder().setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setBaseAlpha(0.3f).setClipToChildren(true).setDropoff(0.5f).setTilt(36f)
                .setShape(Shimmer.Shape.LINEAR).setDuration(1000L).setFixedHeight(100)
                .setRepeatMode(ValueAnimator.RESTART).build()
        // Clear backstack navigation
        findNavController().clearBackStack(R.id.loginFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.shimmerLayout.setShimmer(shimmerBuilder)
        shimmerActive(true)
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
        loadDataInit()
    }

    /** Set Recycler View */
    private fun setUpTrending() {
        val adapter = TrendingAdapter(this)
        binding.trendingRecycleView.adapter = adapter
        binding.trendingRecycleView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        lifecycleScope.launch {
            viewModel.trendingData().collect { listMovies ->
                when (listMovies) {
                    is StateListResult.Success<*> -> {
                        adapter.submitList(listMovies.value as List<ModelMovie>)
                        // Stop Shimmer
                        shimmerActive(false)
                    }

                    is StateListResult.Error -> Toast.makeText(
                        requireContext(), "${listMovies.message}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.trendingRecycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.trendingRecycleView.canScrollHorizontally(1)) {
                    val page = adapter.pageIncrease()
                    if (page != -1 && page != 2) loadData {
                        viewModel.getTrendingData(page = page)
                    }
                    else if (page == -1) binding.trendingRecycleView.scrollToPosition(0)
                }
            }
        })
        binding.txtTrending.setOnClickListener {
            binding.trendingRecycleView.scrollToPosition(0)
        }
    }

    private fun setUpForYou() {
        val adapter = ForYouAdapter(this)
        binding.forYouRecycle.adapter = adapter
        binding.forYouRecycle.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
        lifecycleScope.launch {
            viewModel.forYouData().collect { listMovies ->
                when (listMovies) {
                    is StateListResult.Success<*> -> adapter.submitList(listMovies.value as List<ModelMovie>)

                    is StateListResult.Error -> Toast.makeText(
                        requireContext(), "${listMovies.message}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.forYouRecycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.forYouRecycle.canScrollHorizontally(1)) {
                    val page = adapter.pageIncrease()
                    if (page != 2 && page != -1) loadData {
                        viewModel.getForYouData(page = page)
                    }
                    else if (page == -1) binding.forYouRecycle.scrollToPosition(0)
                }
            }
        })
        binding.txtForYou.setOnClickListener {
            binding.forYouRecycle.scrollToPosition(0)
        }
    }

    private fun setUpCategory() {
        /** Category Movie */
        val adapter = CategoryAdapter()
        binding.categoryRecycler.adapter = adapter
        binding.categoryRecycler.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
        /** Category Movie List */
        binding.categoryListRecycler.layoutManager = GridLayoutManager(context, 2)
        val nowPlayingAdapter = CategoryMovieAdapter(this)
        val popularAdapter = CategoryMovieAdapter(this)
        val upComingAdapter = CategoryMovieAdapter(this)

        // Receive from flow in viewModel
        lifecycleScope.launch {
            viewModel.nowPlayingMovie().collect { listMovie: StateListResult ->
                when (listMovie) {
                    is StateListResult.Success<*> -> nowPlayingAdapter.submitList(listMovie.value as List<ModelMovie>)

                    is StateListResult.Error -> Toast.makeText(
                        requireContext(), "${listMovie.message as String}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.popularMovie().collect { listMovie: StateListResult ->
                when (listMovie) {
                    is StateListResult.Success<*> -> popularAdapter.submitList(listMovie.value as List<ModelMovie>)

                    is StateListResult.Error -> Toast.makeText(
                        requireContext(), "${listMovie.message}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.upComingMovie().collect { listMovie ->
                if (listMovie != null) when (listMovie) {
                    is StateListResult.Success<*> -> upComingAdapter.submitList(listMovie.value as List<ModelMovie>)

                    is StateListResult.Error -> Toast.makeText(
                        requireContext(), "${listMovie.message}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        adapter.currentIndex().observe(viewLifecycleOwner) { value: Int ->
            binding.categoryListRecycler.adapter = when (value) {
                0 -> {
                    if (nowPlayingAdapter.pageCurrent() == 1) loadData {
                        viewModel.getNowPlayingData(page = nowPlayingAdapter.pageIncrease())
                    }
                    nowPlayingAdapter
                }

                1 -> {
                    if (popularAdapter.pageCurrent() == 1) loadData {
                        viewModel.getPopularMovieData(page = popularAdapter.pageIncrease())
                    }
                    popularAdapter
                }

                2 -> {
                    if (upComingAdapter.pageCurrent() == 1) loadData {
                        viewModel.getUpComingData(upComingAdapter.pageIncrease())
                    }
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
                    when (adapter.currentIndex().value) {
                        0 -> {
                            val page = nowPlayingAdapter.pageIncrease()
                            if (page != -1 && page != 1) loadData {
                                viewModel.getNowPlayingData(page = page)
                            }
                            else if (page == -1) binding.categoryListRecycler.scrollToPosition(0)
                        }

                        1 -> {
                            val page = popularAdapter.pageIncrease()
                            if (page == -1) binding.categoryListRecycler.scrollToPosition(0)
                            else loadData {
                                viewModel.getPopularMovieData(page = page)
                            }
                        }

                        2 -> {
                            val page = upComingAdapter.pageIncrease()
                            if (page == -1) binding.categoryListRecycler.scrollToPosition(0)
                            else loadData {
                                viewModel.getUpComingData(page = page)
                            }
                        }
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
    override fun onClick(value: ModelMovie?, imageView: ImageView?) {
        if (imageView != null && value != null && value.id != null) {
            val pairTitle: Pair<View, String> = Pair(imageView, "Cigrcham")

            val extras = FragmentNavigatorExtras(pairTitle)
            val destination = HomeScreenDirections.actionHomeFragmentToDetailScreen(value.id)
            findNavController().navigate(destination, extras)

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
                Toast.makeText(
                    requireContext(), getString(R.string.continue_develop), Toast.LENGTH_SHORT
                ).show()
                true
            }

            R.id.vietName_menu -> {
                (activity as ContainerActivity).changeLangToVietnamese()
                true
            }

            R.id.english_menu -> {
                (activity as ContainerActivity).changeLangToEnglish()
                true
            }

            R.id.message_menu -> {
                Toast.makeText(
                    requireContext(), getString(R.string.continue_develop), Toast.LENGTH_SHORT
                ).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    /** Load Data */
    private inline fun loadData(crossinline invoke: () -> Unit = {}) {
        if (networkStatus.isOnline()) {
            invoke.invoke()
        } else {
            networkStatus.networkState().observe(viewLifecycleOwner) { value: Boolean ->
                if (value) {
                    invoke.invoke()
                    networkStatus.networkState().removeObservers(viewLifecycleOwner)
                }
            }
        }
    }

    private fun loadDataInit() = loadData {
        viewModel.getTrendingData(page = 1)
        viewModel.getForYouData(page = 1)
        viewModel.getNowPlayingData(page = 1)
    }

    private fun shimmerActive(active: Boolean) {
        if (active) {
            binding.scrollContainer.visibility = View.GONE
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmer()
        } else {
            binding.scrollContainer.visibility = View.VISIBLE
            binding.shimmerLayout.visibility = View.GONE
            binding.shimmerLayout.stopShimmer()
        }
    }
}