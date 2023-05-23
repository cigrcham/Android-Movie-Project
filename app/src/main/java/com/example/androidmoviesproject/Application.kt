package com.example.androidmoviesproject

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import com.example.androidmoviesproject.broadcast.NetworkStatus
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Application : Application() {

    @Inject
    lateinit var networkStatus: NetworkStatus
    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkStatus, intentFilter)
        Log.d("NetWorkStatus", "onCreate: $networkStatus")
    }
}
