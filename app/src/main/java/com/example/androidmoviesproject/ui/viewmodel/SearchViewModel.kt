package com.example.androidmoviesproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.data.model.ModelMovie
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
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _recommendData: MutableStateFlow<List<ModelMovie>?> = MutableStateFlow(null)
    fun recommendData(): Flow<List<ModelMovie>> = _recommendData.filterNotNull()
    fun getRecommendData(page: Int) {
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
            _recommendData.emit(listMovies)
        }
    }

    private val _searchData: MutableStateFlow<List<ModelMovie>?> = MutableStateFlow(null)

    fun searchData(): Flow<List<ModelMovie>> = _searchData.filterNotNull()

    fun getSearchData(search: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val listMovies: MutableList<ModelMovie> = mutableListOf()
            repository.getSearchMovie(search = search, page = page).collect {
                when (it) {
                    is StateResult.Success<*> -> {
                        listMovies.add(it.value as ModelMovie)
                    }

                    is StateResult.Error -> {
                    }
                }
            }
            _searchData.emit(listMovies)
        }
    }

}