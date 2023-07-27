package com.example.androidmoviesproject.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidmoviesproject.data.local.converter.CreditsConverter
import com.example.androidmoviesproject.data.local.dao.MovieDao
import com.example.androidmoviesproject.data.local.model.ModelMovieDao

@Database(entities = [ModelMovieDao::class], version = 1, exportSchema = false)
@TypeConverters(CreditsConverter::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    companion object {
        const val DATABASE_NAME = "movieDatabase.db"
        const val MOVIE_TABLE = "movie_table"
    }
}