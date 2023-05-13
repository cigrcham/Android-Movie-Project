package com.example.androidmoviesproject.data.model

import com.google.gson.annotations.SerializedName

data class ModelMovies(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("results") val results: List<ModelMovie>? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("total_results") val totalResults: Int? = null
)