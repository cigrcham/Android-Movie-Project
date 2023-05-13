package com.example.androidmoviesproject.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ModuleContext {
    /** Hilt provide Context Application */
    @Binds
    fun provideApplicationContext(@ApplicationContext context: Context): Context
}