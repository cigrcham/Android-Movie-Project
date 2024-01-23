package com.example.androidmoviesproject.data.model.actorMovie

import com.google.gson.annotations.SerializedName

data class ModelCredits(
    @SerializedName("cast") val cast: List<Cast?>? = null,
    @SerializedName("crew") val crew: List<Crew>? = null,
    @SerializedName("id") val id: Int
)