package com.example.androidmoviesproject.utils

import com.example.androidmoviesproject.R

object Constants {
    const val FIREBASE_AUTH_KEY = "firebase_auth"
    const val NETWORK_NOTIFICATION_ID: Int = 1
    val CATEGORY_MOVIE: List<Int> = listOf(
        R.string.nowplaying,
        R.string.popular,
        R.string.get_upComing,
    )
    const val NETWORK_STATUS: String = "hilt_network_status"
    const val DISCONNECT_NETWORK: String = "Reconnection Network"
    const val LOGIN_FAILURE: String = "Login Failure"
    const val CREATE_ACCOUNT_SUCCESS: String = "Create Account Success"
    const val CREATE_ACCOUNT_FAILURE: String = "Create Account Failure"
    const val NOT_HAVE_ACCOUNT: String = "Haven't Account"
    const val LINK_URL_IMAGE: String = "https://image.tmdb.org/t/p/original/"
}