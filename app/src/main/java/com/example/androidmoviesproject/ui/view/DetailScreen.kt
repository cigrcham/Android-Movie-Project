package com.example.androidmoviesproject.ui.view

import android.animation.ValueAnimator
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.util.Log
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionScene
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import coil.load
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.adapter.actorDetail.ActorsAdapter
import com.example.androidmoviesproject.broadcast.NetworkStatus
import com.example.androidmoviesproject.data.model.actorMovie.ModelCredits
import com.example.androidmoviesproject.data.model.detailMovie.ModelDetailMovie
import com.example.androidmoviesproject.data.repository.Repository
import com.example.androidmoviesproject.databinding.FragmentDetailScreenBinding
import com.example.androidmoviesproject.ui.viewmodel.DetailViewModel
import com.example.androidmoviesproject.utils.Constants
import com.example.androidmoviesproject.utils.Constants.DISCONNECT_NETWORK
import com.example.androidmoviesproject.utils.Constants.LINK_URL_IMAGE
import com.example.androidmoviesproject.utils.Constants.NETWORK_STATUS
import com.example.androidmoviesproject.utils.StateResult
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import okhttp3.internal.http.HTTP_GONE
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class DetailScreen : Fragment(R.layout.fragment_detail_screen) {
    private lateinit var binding: FragmentDetailScreenBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var shimmerBuilder: Shimmer

    @Inject
    @Named(NETWORK_STATUS)
    lateinit var networkStatus: NetworkStatus
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.transition_transform)

        shimmerBuilder =
            Shimmer.AlphaHighlightBuilder().setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setBaseAlpha(0.3f).setClipToChildren(true).setDropoff(0.5f).setTilt(36f)
                .setShape(Shimmer.Shape.LINEAR).setDuration(1000L).setFixedHeight(100)
                .setRepeatMode(ValueAnimator.RESTART).build()
    }


    private val args: DetailScreenArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = args.value

        binding.shimmerLayout.setShimmer(shimmerBuilder)
        shimmerActive(true)


        lifecycleScope.launch {
            viewModel.detailMovie().collect { detailMovie ->
                when (detailMovie) {
                    is StateResult.Success<*> -> setUpView(detailMovie.value as ModelDetailMovie)

                    is StateResult.Error -> Toast.makeText(
                        requireContext(), "${detailMovie.message.toString()}", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.creditMovie().collect { creditMovie ->
                when (creditMovie) {
                    is StateResult.Success<*> -> setUpCredits(creditMovie.value as ModelCredits)

                    is StateResult.Error -> {}
                }
            }
        }
        setUpToolBar()
        var firstCount = true
        if (movieId != null) {
            networkStatus.networkState().observe(viewLifecycleOwner) {
                if (it == true && firstCount) {
                    viewModel.getDetailMovie(movieId = movieId)
                    viewModel.getCreditsMovie(movieId = movieId)
                    firstCount = false
                } else Toast.makeText(requireContext(), "$DISCONNECT_NETWORK", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setUpView(movie: ModelDetailMovie) {
        // Set name Movie for title Fragment
        binding.toolbar.title = movie.title
        // Set View
        binding.nameMovie.text = movie.title
//        binding.productMoive.text = movie.production_countries[0].name
        binding.yearMovie.text = movie.releaseDate
        binding.descriptionMovie.text = movie.overview
        binding.imageMovie.load(LINK_URL_IMAGE + movie.posterPath) {
            crossfade(true)
        }
        val countriesList = movie.productionCountries
        if (!countriesList.isNullOrEmpty()) {
            var sb: StringBuilder = StringBuilder()
            for (count in 0 until countriesList.size - 1) {
                sb.append(countriesList[count].name).append(", ")
            }
            sb.append(countriesList[countriesList.lastIndex].name)
            binding.productMoive.text = sb.toString()
            sb.clear()
        }
        shimmerActive(false)
    }

    private fun setUpCredits(credits: ModelCredits) {
        val adapter = ActorsAdapter()
        if (credits.cast != null) adapter.submitList(credits.cast)
        binding.actorsMovieRecycle.adapter = adapter
        binding.actorsMovieRecycle.layoutManager = GridLayoutManager(context, 2)
    }

    private fun setUpToolBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        val navController: NavController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController = navController, appBarConfiguration)
        binding.toolbar.title = " "
    }

    private fun shimmerActive(active: Boolean) {
        binding.motionLayout.setTransition(R.id.not_To_Data)
        if (active) {
            binding.motionLayout.transitionToStart()
            binding.shimmerLayout.startShimmer()
        } else {
            binding.motionLayout.transitionToEnd()
            binding.shimmerLayout.stopShimmer()
        }
    }
}