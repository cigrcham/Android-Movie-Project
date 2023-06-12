package com.example.androidmoviesproject.data.firebase

import com.example.androidmoviesproject.data.model.Account
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthentication {
    fun loginAccount(
        account: Account,
        success: (FirebaseUser?) -> Unit = {},
        failure: (String?) -> Unit = {}
    )

    fun updateName(
        user: FirebaseUser,
        displayName: String,
        success: () -> Unit = {},
        failure: (String?) -> Unit = {}
    )

    fun updateEmail(
        user: FirebaseUser,
        email: String,
        success: () -> Unit = {},
        failure: (String?) -> Unit = {}
    )

    fun updatePassword(
        user: FirebaseUser,
        password: String,
        success: () -> Unit = {},
        failure: (String?) -> Unit = {}
    )

    fun sendResetEmail(
        email: String
    )

    fun firebaseAuthWithGoogle(
        idToken: String,
        success: (FirebaseUser?) -> Unit = {},
        failure: (String?) -> Unit = {}
    )

    fun createAccount(
        account: Account,
        success: (String?) -> Unit,
        failure: (String?) -> Unit = {}
    )

    fun signOut()
    fun getAccount(): FirebaseUser?
}