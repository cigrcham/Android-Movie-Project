package com.example.androidmoviesproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.data.model.actorMovie.ModelCredits
import com.example.androidmoviesproject.data.model.detailMovie.ModelDetailMovie
import com.example.androidmoviesproject.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    fun getDetailMovie(movieId: Int, data: (ModelDetailMovie) -> Unit = {}) =
        viewModelScope.launch {
            repository.getDetailMovie(movieId = movieId, data = {
                if (it != null) data.invoke(it)
            })
        }

    fun getActorOfMovie(movieId: Int, data: (ModelCredits) -> Unit = {}) = viewModelScope.launch {
        repository.getCreditsMovie(movieId = movieId) {
            if (it != null) data.invoke(it)
        }
    }
}