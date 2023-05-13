package com.example.androidmoviesproject.di

import com.example.androidmoviesproject.data.repository.Repository
import com.example.androidmoviesproject.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**  Provider Repository */
@Module
@InstallIn(SingletonComponent::class)
interface ModuleRepository {
    @Binds
    @Singleton
    fun provideRepository(impl: RepositoryImpl): Repository
}