package com.example.androidmoviesproject.utils

sealed class StateResult {
    data class Success<out T : Any>(val value: T?) : StateResult()
    data class Error(val message: String? = null) : StateResult()
}