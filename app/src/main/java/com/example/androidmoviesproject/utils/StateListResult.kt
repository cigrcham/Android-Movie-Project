package com.example.androidmoviesproject.utils

sealed class StateListResult {
    data class Success<out T : Any>(val value: T) : StateListResult()
    data class Error(val message: String? = null) : StateListResult()
}