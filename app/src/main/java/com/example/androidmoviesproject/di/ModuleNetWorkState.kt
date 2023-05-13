package com.example.androidmoviesproject.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Hilt Provide BroadCast Network */
@Module
@InstallIn(SingletonComponent::class)
class ModuleNetWorkState {
    @Provides
    @Singleton
    fun provideNetworkReceiver() = ModuleNetWorkState()
}