package com.example.androidmoviesproject.utils

import com.example.androidmoviesproject.data.local.model.ModelMovieDao
import com.example.androidmoviesproject.data.model.actorMovie.ModelCredits
import com.example.androidmoviesproject.data.model.detailMovie.ModelDetailMovie

fun convertModelMovieDao(movie: ModelDetailMovie, credits: ModelCredits): ModelMovieDao {
    return ModelMovieDao(
        movieId = movie.id,
        title = movie.title,
        releaseDate = movie.releaseDate,
        overview = movie.overview,
        posterPath = movie.posterPath,
        productionCountries = movie.productionCountries.let { countries ->
            var result: String? = null
            if (!countries.isNullOrEmpty()) {
                var sb: StringBuilder = StringBuilder()
                for (count: Int in 0 until countries.size - 1) {
                    sb.append(countries[count].name).append(", ")
                }
                sb.append(countries[countries.lastIndex].name)
                result = sb.toString()
                sb.clear()
            }
            result
        },
        credits = credits
    )
}