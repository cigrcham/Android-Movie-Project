package com.example.androidmoviesproject.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.data.repository.Repository
import com.example.androidmoviesproject.utils.StateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val TAG: String = this::class.java.simpleName
    init {
            getTrendingData()
            getForYouData()
    }
    private val _trendData: MutableStateFlow<List<ModelMovie>?> = MutableStateFlow(null)
    fun trendingData(): StateFlow<List<ModelMovie>?> = _trendData
    fun getTrendingData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            val listMovies: MutableList<ModelMovie> = mutableListOf()
            repository.getTrendingMovies(page).collect {
                when (it) {
                    is StateResult.Success<*> -> {
                        listMovies.add(it.value as ModelMovie)
                        Log.d(TAG, "trendingData: ${it.value}")
                    }

                    is StateResult.Error -> {
                        Log.d(TAG, "trendingData: Trending Data Error!")
                    }
                }
            }
            _trendData.emit(listMovies)
        }
    }

    private val _forYouData: MutableStateFlow<List<ModelMovie>?> = MutableStateFlow(null)
    fun forYouData(): StateFlow<List<ModelMovie>?> = _forYouData
    fun getForYouData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            val listMovies: MutableList<ModelMovie> = mutableListOf()
            repository.getForYouMovies(page).collect {
                when (it) {
                    is StateResult.Success<*> -> {
                        listMovies.add(it.value as ModelMovie)
                        Log.d(TAG, "forYouData: ${it.value}")
                    }

                    is StateResult.Error -> {
                        Log.d(TAG, "forYouData: For You Data Error!")
                    }
                }
            }
            _forYouData.emit(listMovies)
        }
    }

    private val _nowPlayingMovie: MutableStateFlow<List<ModelMovie>?> = MutableStateFlow(null)
    fun nowPlayingMovie(): StateFlow<List<ModelMovie>?> = _nowPlayingMovie
    fun getNowPlayingData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            val listMovies: MutableList<ModelMovie> = mutableListOf()
            repository.getNowPlaying(page).collect {
                when (it) {
                    is StateResult.Success<*> -> {
                        listMovies.add(it.value as ModelMovie)
                        Log.d(TAG, "getNowPlayingData: ${it.value}")
                    }

                    is StateResult.Error -> {
                        Log.d(TAG, "getNowPlayingData: For You Data Error!")
                    }
                }
            }
            _nowPlayingMovie.emit(listMovies)
        }
    }

    private val _popularMovie: MutableStateFlow<List<ModelMovie>?> = MutableStateFlow(null)
    fun popularMovie(): StateFlow<List<ModelMovie>?> = _popularMovie
    fun getPopularMovieData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            val listMovies: MutableList<ModelMovie> = mutableListOf()
            repository.getPopular(page).collect {
                when (it) {
                    is StateResult.Success<*> -> {
                        listMovies.add(it.value as ModelMovie)
                        Log.d(TAG, "getPopularMovieData: ${it.value}")
                    }

                    is StateResult.Error -> {
                        Log.d(TAG, "getPopularMovieData: For You Data Error!")
                    }
                }
            }
            _popularMovie.emit(listMovies)
        }
    }

    private val _upComingMovie: MutableStateFlow<List<ModelMovie>?> = MutableStateFlow(null)
    fun upComingMovie(): StateFlow<List<ModelMovie>?> = _upComingMovie
    fun getUpComingData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            val listMovies: MutableList<ModelMovie> = mutableListOf()
            repository.getUpcoming(page).collect {
                when (it) {
                    is StateResult.Success<*> -> {
                        listMovies.add(it.value as ModelMovie)
                        Log.d(TAG, "getUpComingData: ${it.value}")
                    }

                    is StateResult.Error -> {
                        Log.d(TAG, "forYouData: For You Data Error!")
                    }
                }
            }
            _upComingMovie.emit(listMovies)
        }
    }
}