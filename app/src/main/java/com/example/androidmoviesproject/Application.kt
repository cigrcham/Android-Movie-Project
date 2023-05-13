package com.example.androidmoviesproject

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.example.androidmoviesproject.broadcast.NetworkState
import com.example.androidmoviesproject.data.remote.api.ApiMovie
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Application : Application() {
    @Inject
    lateinit var apiMovie: ApiMovie

    @Inject
    lateinit var netWorkState: NetworkState
    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(netWorkState, intentFilter)
    }
}