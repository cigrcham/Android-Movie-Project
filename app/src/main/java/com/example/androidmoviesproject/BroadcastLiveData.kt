package com.example.androidmoviesproject

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData

//class BroadcastLiveData(private val context:Context): LiveData<String>() {
//    private val receiver = object :BroadcastReceiver(){
//        override fun onReceive(context: Context?, intent: Intent?) {
//            postValue("context")
//        }
//    }
//    private val intentFilter = IntentFilter()
//    override fun onActive() {
//        super.onActive()
//        context.registerReceiver(receiver,intentFilter)
//    }
//
//    override fun onInactive() {
//        super.onInactive()
//        context.unregisterReceiver(receiver)
//    }
//}