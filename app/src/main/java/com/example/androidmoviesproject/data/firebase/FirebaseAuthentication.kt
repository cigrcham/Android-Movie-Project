package com.example.androidmoviesproject.data.firebase

import com.example.androidmoviesproject.data.model.Account
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthentication {
    fun loginAccount(
        account: Account,
        success: (FirebaseUser?) -> Unit = {},
        failure: () -> Unit = {}
    )

    fun updateName(
        user: FirebaseUser,
        displayName: String,
        success: () -> Unit = {},
        failure: () -> Unit = {}
    )

    fun updateEmail(
        user: FirebaseUser,
        email: String,
        success: () -> Unit = {},
        failure: () -> Unit = {}
    )

    fun updatePassword(
        user: FirebaseUser,
        password: String,
        success: () -> Unit = {},
        failure: () -> Unit = {}
    )

    fun sendResetEmail(
        email: String
    )

    fun firebaseAuthWithGoogle(
        idToken: String,
        success: ( FirebaseUser?) -> Unit = {},
        failure: () -> Unit = {}
    )
    fun createAccount(account: Account, success: () -> Unit, failure: () -> Unit = {})}