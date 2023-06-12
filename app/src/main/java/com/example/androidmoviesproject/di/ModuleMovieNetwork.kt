package com.example.androidmoviesproject.di

import android.content.Context
import com.example.androidmoviesproject.BuildConfig
import com.example.androidmoviesproject.data.remote.api.ApiMovie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/** Hilt Retrofit Provider */
@InstallIn(SingletonComponent::class)
@Module
class ModuleMovieNetwork {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/"
        private const val ACCESS_KEY = BuildConfig.ACCESS_KEY_TBDB
    }

    @Singleton
    @Provides
    fun provideHttpCache(context: Context): Cache =
        Cache(context.cacheDir, (10 * 1024 * 1024).toLong())

    @Singleton
    @Provides
    @Named("Logging")
    fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    @Named("Header")
    fun provideHeaderInterceptor(): Interceptor = Interceptor { chain: Interceptor.Chain ->
        val origin: Request = chain.request()
        val newURL = origin.url.newBuilder().addQueryParameter("api_key", ACCESS_KEY).build()
        val newRequest =
            origin.newBuilder().url(newURL).header("Authorization", "Bearer $ACCESS_KEY")
                .method(origin.method, origin.body).build()
        chain.proceed(newRequest)
    }

    @Singleton
    @Provides
    fun provideClient(
        cache: Cache,
        @Named("Logging") loggingInterceptor: Interceptor,
        @Named("Header") headerInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder().cache(cache = cache).connectTimeout(5, TimeUnit.SECONDS)
            .callTimeout(timeout = 60, unit = TimeUnit.SECONDS).addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor).build()

    @Singleton
    @Provides
    fun provideMovieApi(
        client: OkHttpClient
    ): ApiMovie = Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(
        GsonConverterFactory.create()
    ).build().create(ApiMovie::class.java)
}