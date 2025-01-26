package com.example.androidmoviesproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.data.firebase.FirebaseAuthentication
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.data.repository.Repository
import com.example.androidmoviesproject.utils.StateListResult
import com.example.androidmoviesproject.utils.StateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val firebaseAuth: FirebaseAuthentication,
) : ViewModel() {

    private val _trendData: MutableStateFlow<StateListResult?> = MutableStateFlow(null)
    fun trendingData(): Flow<StateListResult> = _trendData.filterNotNull()
    fun getTrendingData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            val listMovies: MutableList<ModelMovie> = mutableListOf()
            repository.getTrendingMovies(page).collect {
                when (it) {
                    is StateResult.Success<*> -> {
                        listMovies.add(it.value as ModelMovie)
                    }

                    is StateResult.Error -> {
                        _trendData.emit(StateListResult.Error(message = it.message))
                    }
                }
            }
            _trendData.emit(StateListResult.Success(value = listMovies))
        }
    }

    private val _forYouData: MutableStateFlow<StateListResult?> = MutableStateFlow(null)
    fun forYouData(): Flow<StateListResult> = _forYouData.filterNotNull()
    fun getForYouData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
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
        }
    }

    private val _nowPlayingMovie: MutableStateFlow<StateListResult?> = MutableStateFlow(null)
    fun nowPlayingMovie(): Flow<StateListResult> = _nowPlayingMovie.filterNotNull()
    fun getNowPlayingData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
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
        }
    }

    private val _popularMovie: MutableStateFlow<StateListResult?> = MutableStateFlow(null)
    fun popularMovie(): Flow<StateListResult> = _popularMovie.filterNotNull()
    fun getPopularMovieData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
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
        }
    }

    private val _upComingMovie: MutableStateFlow<StateListResult?> = MutableStateFlow(null)
    fun upComingMovie(): Flow<StateListResult> = _upComingMovie.filterNotNull()
    fun getUpComingData(page: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
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
        }
    }

    fun signOutAccount() {
        firebaseAuth.signOut()
    }
}