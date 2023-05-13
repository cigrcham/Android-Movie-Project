package com.example.androidmoviesproject.data.remote.api

import com.example.androidmoviesproject.data.model.detailMovie.ModelDetailMovie
import com.example.androidmoviesproject.data.model.ModelMovies
import com.example.androidmoviesproject.data.model.actorMovie.ModelCredits
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMovie {
    /** Get Trending Movies */
    @GET("3/trending/movie/day")
    suspend fun getTrendingMovie(@Query("page") page: Int = 1): Call<ModelMovies>?

    @GET("3/trending/movie/day")
    suspend fun getTrendingMovieTemp(@Query("page") page: Int): ModelMovies?

    /** Get For you Movies */
    @GET("3/movie/top_rated")
    suspend fun getForYouMovie(@Query("page") page: Int): ModelMovies?

    /** Get Now Playing Movie */
    @GET("3/movie/now_playing")
    suspend fun getNowPlaying(@Query("page") page: Int): ModelMovies?

    /** Popular Movie */
    @GET("3/movie/popular")
    suspend fun getPopularMovie(@Query("page") page: Int): ModelMovies?

    /** UpComing Movie */
    @GET("3/movie/upcoming")
    suspend fun getUpComingMovie(@Query("page") page: Int): ModelMovies?

    /** Detail Movie */
    @GET("3/movie/{movie_id}")
    suspend fun getDetailMovie(@Path("movie_id") movieId: Int): ModelDetailMovie?

    /** Get Credits */
    @GET("3/movie/{movie_id}/credits")
    suspend fun getCreditsMovie(@Path("movie_id") movieId: Int): ModelCredits?

    /** Get Search*/
    @GET("3/search/multi")
    suspend fun getSearch(@Query("query") query: String, @Query("page") page: Int): ModelMovies?
}