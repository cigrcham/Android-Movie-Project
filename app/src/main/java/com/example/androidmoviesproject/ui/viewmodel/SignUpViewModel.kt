package com.example.androidmoviesproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.androidmoviesproject.data.model.Account
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    fun createAccount(account: Account, success: () -> Unit, failure: () -> Unit = {}) {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(account.userName, account.passWord)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    success.invoke()
                } else {
                    failure.invoke()
                }
            }
            .addOnFailureListener {
                failure.invoke()
            }
    }
}