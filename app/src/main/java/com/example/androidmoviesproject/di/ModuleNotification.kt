package com.example.androidmoviesproject.di

import com.example.androidmoviesproject.data.firebase.FirebaseNotification
import com.example.androidmoviesproject.data.firebase.FirebaseNotificationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ModuleNotification {
    @Binds
    fun provideFirebaseNotification(notify: FirebaseNotificationImpl): FirebaseNotification
}