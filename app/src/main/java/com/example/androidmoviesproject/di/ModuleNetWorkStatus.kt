package com.example.androidmoviesproject.di

import com.example.androidmoviesproject.broadcast.NetworkStatus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Hilt Provide BroadCast Network */
@Module
@InstallIn(SingletonComponent::class)
object ModuleNetWorkStatus {
    @Provides
    @Singleton
    fun provideNetworkReceiver(): NetworkStatus {
        return NetworkStatus()
    }
}