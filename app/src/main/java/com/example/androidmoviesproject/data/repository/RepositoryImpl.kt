package com.example.androidmoviesproject.data.repository

import android.util.Log
import com.example.androidmoviesproject.data.model.actorMovie.ModelCredits
import com.example.androidmoviesproject.data.model.detailMovie.ModelDetailMovie
import com.example.androidmoviesproject.data.remote.api.ApiMovie
import com.example.androidmoviesproject.utils.StateResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val movieApi: ApiMovie
) : Repository {
    private val TAG: String = this::class.java.simpleName

    override suspend fun getTrendingMovies(page: Int): Flow<StateResult> = flow {
        Log.d(TAG, "Get Trending Movies. ${Thread.currentThread().name} and $page")
        movieApi.getTrendingMovieTemp(page = page)?.results?.toMutableList()?.forEach { value ->
            this.emit(StateResult.Success(value))
        }
    }

    override suspend fun getForYouMovies(page: Int): Flow<StateResult> = flow {
        Log.d(TAG, "Get For You Movies: ${Thread.currentThread().name} and $page")
        movieApi.getForYouMovie(page = page)?.results?.toMutableList()?.forEach { value ->
            this.emit(StateResult.Success(value))
        }
    }

    override suspend fun getNowPlaying(page: Int): Flow<StateResult> = flow {
        Log.d(TAG, "getNowPlaying: ${Thread.currentThread().name} and $page")
        movieApi.getNowPlaying(page = page)?.results?.toMutableList()?.forEach { value ->
            this.emit(StateResult.Success(value))
        }
    }

    override suspend fun getPopular(page: Int): Flow<StateResult> = flow {
        movieApi.getPopularMovie(page = page)?.results?.toMutableList()?.forEach { value ->
            this.emit(StateResult.Success(value))
        }
    }

    override suspend fun getUpcoming(page: Int): Flow<StateResult> = flow {
        movieApi.getUpComingMovie(page = page)?.results?.toMutableList()?.forEach { value ->
            this.emit(StateResult.Success(value))
        }
    }

    override suspend fun getDetailMovie(
        movieId: Int
    ): ModelDetailMovie? =
        movieApi.getDetailMovie(movieId = movieId)

    override suspend fun getCreditsMovie(movieId: Int): ModelCredits? =
        movieApi.getCreditsMovie(movieId = movieId)

    override suspend fun getSearchMovie(search: String, page: Int): Flow<StateResult> = flow {
        Log.d(TAG, "Get Search Movie. ${Thread.currentThread().name} and $page")
        movieApi.getSearch(query = search, page = page)?.results?.toMutableList()
            ?.forEach { value ->
                this.emit(StateResult.Success(value))
            }
    }


}
