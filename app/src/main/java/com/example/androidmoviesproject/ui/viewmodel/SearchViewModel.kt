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
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val TAG: String = this::class.java.simpleName


    private val _recommendData: MutableStateFlow<List<ModelMovie>?> = MutableStateFlow(null)
    fun recommendData(): StateFlow<List<ModelMovie>?> = _recommendData
    fun getRecommendData(page: Int = 1) {
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
            _recommendData.emit(listMovies)
        }
    }

    private val _searchData: MutableStateFlow<List<ModelMovie>?> = MutableStateFlow(null)

    fun searchData(): StateFlow<List<ModelMovie>?> = _searchData

    fun getSearchData(search: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val listMovies: MutableList<ModelMovie> = mutableListOf()
            repository.getSearchMovie(search = search, page = page).collect {
                when (it) {
                    is StateResult.Success<*> -> {
                        listMovies.add(it.value as ModelMovie)
                        Log.d(TAG, "Search Movie: ${it.value}")
                    }

                    is StateResult.Error -> {
                        Log.d(TAG, "Search Movie: Search Data Error!")
                    }
                }
            }
            _searchData.emit(listMovies)
        }
    }

}