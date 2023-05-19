package com.example.androidmoviesproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.data.firebase.FirebaseAuthentication
import com.example.androidmoviesproject.data.model.Account
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val firebaseAuth: FirebaseAuthentication) :
    ViewModel() {
    fun loginAccount(
        account: Account,
        success: (FirebaseUser?) -> Unit = {},
        failure: () -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        firebaseAuth.loginAccount(account, success, failure)
    }

    fun updateName(
        user: FirebaseUser,
        displayName: String,
        success: () -> Unit = {},
        failure: () -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        firebaseAuth.updateName(user, displayName, success, failure)
    }

    fun updateEmail(
        user: FirebaseUser,
        email: String,
        success: () -> Unit = {},
        failure: () -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        firebaseAuth.updateEmail(user, email, success, failure)
    }

    fun updatePassword(
        user: FirebaseUser,
        password: String,
        success: () -> Unit = {},
        failure: () -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        firebaseAuth.updatePassword(user, password, success, failure)
    }

    fun sendResetEmail(
        email: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        firebaseAuth.sendResetEmail(email)
    }

    fun firebaseAuthWithGoogle(
        idToken: String,
        success: (FirebaseUser?) -> Unit = {},
        failure: () -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        firebaseAuth.firebaseAuthWithGoogle(idToken, success, failure)
    }
}