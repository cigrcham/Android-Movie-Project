package com.example.androidmoviesproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidmoviesproject.data.firebase.FirebaseAuthentication
import com.example.androidmoviesproject.data.model.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val firebaseAuth: FirebaseAuthentication) :
    ViewModel() {
    fun createAccount(account: Account, success: () -> Unit, failure: () -> Unit = {}) {
        firebaseAuth.createAccount(account, success, failure)
    }
}