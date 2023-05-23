package com.example.androidmoviesproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.broadcast.NetworkStatus
import com.example.androidmoviesproject.data.model.actorMovie.Cast
import com.example.androidmoviesproject.data.model.actorMovie.ModelCredits
import com.example.androidmoviesproject.data.repository.Repository
import com.example.androidmoviesproject.utils.Constants.DISCONNECT_NETWORK
import com.example.androidmoviesproject.utils.StateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
    private val networkStatus: NetworkStatus
) : ViewModel() {
    private val _detailMovie: MutableStateFlow<StateResult?> = MutableStateFlow(null)
    fun detailMovie(): Flow<StateResult> = _detailMovie.filterNotNull()

    fun getDetailMovie(movieId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            if (networkStatus.isOnline()) {
                repository.getDetailMovie(movieId = movieId).also {
                    if (it != null)
                        _detailMovie.emit(StateResult.Success(value = it))
                    else
                        _detailMovie.emit(StateResult.Error("Not Detail Movie"))
                }
            } else
                _detailMovie.emit(StateResult.Error(message = DISCONNECT_NETWORK))
        }

    private val _creditMovie: MutableStateFlow<StateResult?> = MutableStateFlow(null)
    fun creditMovie(): Flow<StateResult> = _creditMovie.filterNotNull()

    fun getCreditsMovie(movieId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            if (networkStatus.isOnline()) {
                repository.getCreditsMovie(movieId = movieId).also {
                    if (it != null)
                        _creditMovie.emit(StateResult.Success(value = it))
                    else
                        _creditMovie.emit(StateResult.Error(message = "Not Credits Movie"))
                }
            } else
                _creditMovie.emit(StateResult.Error(message = DISCONNECT_NETWORK))
        }
}