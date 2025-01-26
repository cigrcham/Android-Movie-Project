package com.example.androidmoviesproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.data.repository.Repository
import com.example.androidmoviesproject.utils.StateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    private val _detailMovie: MutableStateFlow<StateResult?> = MutableStateFlow(null)
    val detailMovie: Flow<StateResult> = _detailMovie.filterNotNull()

    private val _creditMovie: MutableStateFlow<StateResult?> = MutableStateFlow(null)
    val creditMovie: Flow<StateResult> = _creditMovie.filterNotNull()

    fun getDetailMovie(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.getDetailMovie(movieId = movieId).let {
            _detailMovie.value = StateResult.Success(value = it)
        }
    }

    fun getCreditsMovie(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.getCreditsMovie(movieId = movieId).let {
                _creditMovie.emit(StateResult.Success(value = it))
        }
    }
}