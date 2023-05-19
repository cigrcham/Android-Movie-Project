package com.example.androidmoviesproject.ui.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.androidmoviesproject.data.firebase.FirebaseAuthentication
import com.example.androidmoviesproject.data.model.Account
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val firebaseAuth: FirebaseAuthentication) :
    ViewModel() {
    fun loginAccount(
        account: Account,
        success: (FirebaseUser?) -> Unit = {},
        failure: () -> Unit = {}
    ) {
        firebaseAuth.loginAccount(account, success, failure)
    }

    fun updateName(
        user: FirebaseUser,
        displayName: String,
        success: () -> Unit = {},
        failure: () -> Unit = {}
    ) {
        firebaseAuth.updateName(user, displayName, success, failure)
    }

    fun updateEmail(
        user: FirebaseUser,
        email: String,
        success: () -> Unit = {},
        failure: () -> Unit = {}
    ) {

        firebaseAuth.updateEmail(user, email, success, failure)
    }

    fun updatePassword(
        user: FirebaseUser,
        password: String,
        success: () -> Unit = {},
        failure: () -> Unit = {}
    ) {
        firebaseAuth.updatePassword(user, password, success, failure)
    }

    fun sendResetEmail(
        email: String
    ) {
        firebaseAuth.sendResetEmail(email)
    }

    private var auth: FirebaseAuth = Firebase.auth
    fun firebaseAuthWithGoogle(
        idToken: String,
        success: (FirebaseUser?) -> Unit = {},
        failure: () -> Unit = {}
    ) {
        firebaseAuth.firebaseAuthWithGoogle(idToken, success, failure)
    }
}