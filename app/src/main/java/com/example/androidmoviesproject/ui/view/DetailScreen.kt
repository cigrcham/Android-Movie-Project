package com.example.androidmoviesproject.ui.view

import android.os.Bundle
import android.util.Log
import android.view.Display.Mode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.example.androidmoviesproject.data.model.actorMovie.ModelCredits
import com.example.androidmoviesproject.data.model.detailMovie.ModelDetailMovie
import com.example.androidmoviesproject.databinding.FragmentDetailScreenBinding
import com.example.androidmoviesproject.ui.viewmodel.DetailViewModel
import com.example.androidmoviesproject.utils.Constants.LINK_URL_IMAGE
import com.example.androidmoviesproject.utils.StateResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailScreen : Fragment() {
    private lateinit var binding: FragmentDetailScreenBinding
    private val viewModel: DetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.transition_transform)

    }

    private val args: DetailScreenArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = args.value

        lifecycleScope.launch {
            viewModel.detailMovie().collect { detailMovie ->
                when (detailMovie) {
                    is StateResult.Success<*> ->
                        setUpView(detailMovie.value as ModelDetailMovie)

                    is StateResult.Error ->
                        Toast.makeText(
                            requireContext(),
                            "${detailMovie.message.toString()}",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.creditMovie().collect { creditMovie ->
                when (creditMovie) {
                    is StateResult.Success<*> ->
                        setUpCredits(creditMovie.value as ModelCredits)

                    is StateResult.Error -> {}
                }
            }
        }
        setUpToolBar()

        if (movieId != null) {
            viewModel.getDetailMovie(movieId = movieId)
            viewModel.getCreditsMovie(movieId = movieId)
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
    }

    private fun setUpCredits(credits: ModelCredits) {
        val adapter = ActorsAdapter()
        if (credits.cast != null)
            adapter.submitList(credits.cast)
        binding.actorsMovieRecycle.adapter = adapter
        binding.actorsMovieRecycle.layoutManager = GridLayoutManager(context, 2)
    }

    private fun setUpToolBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        val navController: NavController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController = navController, appBarConfiguration)
    }
}