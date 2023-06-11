package com.example.androidmoviesproject.di

import com.example.androidmoviesproject.utils.LanguageUtils
import com.example.androidmoviesproject.utils.LanguageUtils.Companion.LANGUAGE_MODULE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleLanguage {
    @Provides
    @Singleton
    @Named(LANGUAGE_MODULE)
    fun provideLanguageUtils(): LanguageUtils {
        return LanguageUtils()
    }
}