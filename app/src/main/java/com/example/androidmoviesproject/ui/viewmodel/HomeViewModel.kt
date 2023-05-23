package com.example.androidmoviesproject.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.Application
import com.example.androidmoviesproject.broadcast.NetworkStatus
import com.example.androidmoviesproject.data.firebase.FirebaseAuthentication
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.data.repository.Repository
import com.example.androidmoviesproject.utils.Constants.DISCONNECT_NETWORK
import com.example.androidmoviesproject.utils.StateListResult
import com.example.androidmoviesproject.utils.StateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val firebaseAuth: FirebaseAuthentication,
    private val networkStatus: NetworkStatus
) : ViewModel() {
    init {
        getTrendingData()
        getForYouData()
        Log.d("NetWorkStatus", "onCreate: $networkStatus")
    }

    private val _trendData: MutableStateFlow<StateListResult?> = MutableStateFlow(null)
    fun trendingData(): StateFlow<StateListResult?> = _trendData
    fun getTrendingData(page: Int = 1) {
        Log.d("Cigrcham", "getTrendingData: Call Trending $networkStatus")
        viewModelScope.launch(Dispatchers.IO) {
            if (true) {
                val listMovies: MutableList<ModelMovie> = mutableListOf()
                repository.getTrendingMovies(page).collect {
                    when (it) {
                        is StateResult.Success<*> -> {
                            listMovies.add(it.value as ModelMovie)
                        }

                        is StateResult.Error -> {
                        }
                    }
                }
                _trendData.emit(StateListResult.Success(value = listMovies))
            } else
                _trendData.emit(StateListResult.Error(DISCONNECT_NETWORK))
        }
    }

    private val _forYouData: MutableStateFlow<StateListResult?> = MutableStateFlow(null)
    fun forYouData(): StateFlow<StateListResult?> = _forYouData
    fun getForYouData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            if (true) {
                val listMovies: MutableList<ModelMovie> = mutableListOf()
                repository.getForYouMovies(page).collect {
                    when (it) {
                        is StateResult.Success<*> -> {
                            listMovies.add(it.value as ModelMovie)
                        }

                        is StateResult.Error -> {
                        }
                    }
                }
                _forYouData.emit(StateListResult.Success(value = listMovies))
            } else
                _forYouData.emit(StateListResult.Error(DISCONNECT_NETWORK))
        }
    }

    private val _nowPlayingMovie: MutableStateFlow<StateListResult?> = MutableStateFlow(null)
    fun nowPlayingMovie(): StateFlow<StateListResult?> = _nowPlayingMovie
    fun getNowPlayingData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            if (true) {
                val listMovies: MutableList<ModelMovie> = mutableListOf()
                repository.getNowPlaying(page).collect {
                    when (it) {
                        is StateResult.Success<*> -> {
                            listMovies.add(it.value as ModelMovie)
                        }

                        is StateResult.Error -> {
                        }
                    }
                }
                _nowPlayingMovie.emit(StateListResult.Success(value = listMovies))
            } else
                _nowPlayingMovie.emit(StateListResult.Error(message = DISCONNECT_NETWORK))
        }
    }

    private val _popularMovie: MutableStateFlow<StateListResult?> = MutableStateFlow(null)
    fun popularMovie(): StateFlow<StateListResult?> = _popularMovie
    fun getPopularMovieData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            if (true) {
                val listMovies: MutableList<ModelMovie> = mutableListOf()
                repository.getPopular(page).collect {
                    when (it) {
                        is StateResult.Success<*> -> {
                            listMovies.add(it.value as ModelMovie)
                        }

                        is StateResult.Error -> {
                        }
                    }
                }
                _popularMovie.emit(StateListResult.Success(value = listMovies))
            } else
                _popularMovie.emit(StateListResult.Error(message = DISCONNECT_NETWORK))


        }
    }

    private val _upComingMovie: MutableStateFlow<StateListResult?> = MutableStateFlow(null)
    fun upComingMovie(): StateFlow<StateListResult?> = _upComingMovie
    fun getUpComingData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            if (true) {
                val listMovies: MutableList<ModelMovie> = mutableListOf()
                repository.getUpcoming(page).collect {
                    when (it) {
                        is StateResult.Success<*> -> {
                            listMovies.add(it.value as ModelMovie)
                        }

                        is StateResult.Error -> {
                        }
                    }
                }
                _upComingMovie.emit(StateListResult.Success(value = listMovies))
            } else
                _upComingMovie.emit(StateListResult.Error(message = DISCONNECT_NETWORK))
        }
    }

    fun signOutAccount() {
        firebaseAuth.signOut()
    }
}