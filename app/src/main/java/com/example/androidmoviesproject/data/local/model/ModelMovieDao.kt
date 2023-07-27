package com.example.androidmoviesproject.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidmoviesproject.data.local.db.MovieDatabase.Companion.MOVIE_TABLE
import com.example.androidmoviesproject.data.model.actorMovie.ModelCredits

@Entity(tableName = MOVIE_TABLE)
data class ModelMovieDao(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val movieId: Int?,

    @ColumnInfo("title")
    val title: String? = null,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String? = null,

    @ColumnInfo(name = "overview")
    val overview: String? = null,

    @ColumnInfo(name = "posterPath")
    val posterPath: String? = null,

    @ColumnInfo(name = "productionCountries")
    val productionCountries: String? = null,

    @ColumnInfo(name = "credits")
    val credits: ModelCredits? = null
)