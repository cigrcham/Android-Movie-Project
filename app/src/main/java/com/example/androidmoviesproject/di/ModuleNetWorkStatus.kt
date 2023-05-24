package com.example.androidmoviesproject.di

import com.example.androidmoviesproject.broadcast.NetworkStatus
import com.example.androidmoviesproject.utils.Constants.NETWORK_STATUS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/** Hilt Provide BroadCast Network */
@Module
@InstallIn(SingletonComponent::class)
object ModuleNetWorkStatus {
    @Provides
    @Singleton
    @Named(NETWORK_STATUS)
    fun provideNetworkReceiver(): NetworkStatus {
        return NetworkStatus()
    }
}