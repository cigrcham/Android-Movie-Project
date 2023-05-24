package com.example.androidmoviesproject.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.utils.Constants.NETWORK_NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NetworkStatus @Inject constructor() : BroadcastReceiver() {
    /** Save state and emit notification when data change */
    private val _networkState = MutableLiveData(true)
    override fun onReceive(context: Context?, intent: Intent?) {
        val connectivityManager: ConnectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val statusNetwork = connectivityManager.activeNetworkInfo
        changeNetwork(context = context, isConnected = statusNetwork != null)
    }

    private fun changeNetwork(context: Context, isConnected: Boolean) {
        if (_networkState.value != isConnected) {
            _networkState.value = isConnected
            _networkState.postValue(isConnected)
            if (!isConnected) {
                Toast.makeText(context, "Internet Disconnect!", Toast.LENGTH_SHORT).show()
                createNotification(context = context)
            } else {
                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancel(NETWORK_NOTIFICATION_ID)
            }
        }
    }

    /** Get status internet */
    fun isOnline(): Boolean {
        val value = _networkState.value ?: false
        Log.d("NetWorkStatus", "isOnline: $value")
        return value
    }

    fun networkState(): LiveData<Boolean> = _networkState

    /** Display notification when Disconnected */
    private fun createNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "network", "Network", NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        val build =
            NotificationCompat.Builder(context, "network").setSmallIcon(R.drawable.network_24)
                .setContentTitle("Disconnect Network").setContentText("Disconnect to Network")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notificationManager.notify(NETWORK_NOTIFICATION_ID, build.build())
    }
}