package com.example.androidmoviesproject.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ModelNotification(
    val id: String? = null,
    val image: String? = null,
    val read: Boolean? = false,
    val title: String? = null
)