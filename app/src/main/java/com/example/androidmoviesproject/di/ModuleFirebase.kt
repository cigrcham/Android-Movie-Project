package com.example.androidmoviesproject.di

import com.example.androidmoviesproject.utils.Constants.FIREBASE_AUTH_KEY
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleFirebase {
    @Provides
    @Singleton
    @Named(FIREBASE_AUTH_KEY)
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}