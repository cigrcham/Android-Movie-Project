package com.example.androidmoviesproject.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.broadcast.NetworkStatus
import com.example.androidmoviesproject.data.model.actorMovie.ModelCredits
import com.example.androidmoviesproject.data.model.detailMovie.ModelDetailMovie
import com.example.androidmoviesproject.data.repository.Repository
import com.example.androidmoviesproject.utils.Constants.DISCONNECT_NETWORK
import com.example.androidmoviesproject.utils.StateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
    private val networkStatus: NetworkStatus
) : ViewModel() {
    fun getDetailMovie(movieId: Int, data: (ModelDetailMovie) -> Unit = {}) =
        viewModelScope.launch(Dispatchers.IO) {
            if (networkStatus.isOnline()) {
//                repository.getDetailMovie(movieId = movieId, data = {
//                    if (it != null)
//                        data.invoke(StateResult.Success(value = it))
//                    else
//                        data.invoke(StateResult.Error())
//                })
                repository.getDetailMovie(movieId = movieId, data = {
                    if (it != null)
                        data.invoke(it)
                })

            }
//            else
//                data.invoke(StateResult.Error(DISCONNECT_NETWORK))
        }

    fun getActorOfMovie(movieId: Int, data: (ModelCredits) -> Unit = {}) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCreditsMovie(movieId = movieId) {
                if (it != null) data.invoke(it)
            }
        }
}