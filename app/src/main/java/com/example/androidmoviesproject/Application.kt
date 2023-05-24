package com.example.androidmoviesproject

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import com.example.androidmoviesproject.broadcast.NetworkStatus
import com.example.androidmoviesproject.utils.Constants
import com.example.androidmoviesproject.utils.Constants.NETWORK_STATUS
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidApp
class Application : Application() {

    @Inject
    @Named(NETWORK_STATUS)
    lateinit var networkStatus: NetworkStatus
    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkStatus, intentFilter)
    }
}
