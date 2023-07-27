package com.example.androidmoviesproject.di

import android.content.Context
import androidx.room.Room
import com.example.androidmoviesproject.data.local.converter.CreditsConverter
import com.example.androidmoviesproject.data.local.dao.MovieDao
import com.example.androidmoviesproject.data.local.db.MovieDatabase
import com.example.androidmoviesproject.data.local.db.MovieDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleMovieDatabase {
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDao {
        val movieDatabase: MovieDatabase = Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            DATABASE_NAME,
        ).fallbackToDestructiveMigration().addTypeConverter(CreditsConverter()).build()
        return movieDatabase.movieDao()
    }
}