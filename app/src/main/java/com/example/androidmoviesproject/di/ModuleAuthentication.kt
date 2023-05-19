package com.example.androidmoviesproject.di

import com.example.androidmoviesproject.data.firebase.FirebaseAuthentication
import com.example.androidmoviesproject.data.firebase.FirebaseAuthenticationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ModuleAuthentication {
    @Binds
    @Singleton
    fun provideFirebaseAuthentication(auth: FirebaseAuthenticationImpl): FirebaseAuthentication
}