package com.example.androidmoviesproject.data.repository

import com.example.androidmoviesproject.data.model.actorMovie.ModelCredits
import com.example.androidmoviesproject.data.model.detailMovie.ModelDetailMovie
import com.example.androidmoviesproject.utils.StateResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Call


interface Repository {
    suspend fun getTrendingMovies(page: Int): Flow<StateResult>
    suspend fun getForYouMovies(page: Int): Flow<StateResult>
    suspend fun getNowPlaying(page: Int): Flow<StateResult>
    suspend fun getPopular(page: Int): Flow<StateResult>
    suspend fun getUpcoming(page: Int): Flow<StateResult>
    suspend fun getDetailMovie(movieId: Int): ModelDetailMovie?
    suspend fun getCreditsMovie(movieId: Int): ModelCredits?
    suspend fun getSearchMovie(search: String, page: Int): Flow<StateResult>
}