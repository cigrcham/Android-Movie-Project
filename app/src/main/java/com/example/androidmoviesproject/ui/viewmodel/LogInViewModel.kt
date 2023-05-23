package com.example.androidmoviesproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.broadcast.NetworkStatus
import com.example.androidmoviesproject.data.firebase.FirebaseAuthentication
import com.example.androidmoviesproject.data.model.Account
import com.example.androidmoviesproject.utils.Constants.DISCONNECT_NETWORK
import com.example.androidmoviesproject.utils.Constants.NOT_HAVE_ACCOUNT
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuthentication,
    private val networkStatus: NetworkStatus
) :
    ViewModel() {
    fun loginAccount(
        account: Account,
        success: (FirebaseUser?) -> Unit = {},
        failure: (String?) -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (networkStatus.isOnline()) {
            firebaseAuth.loginAccount(account, success, failure)
        } else
            failure.invoke(DISCONNECT_NETWORK)
    }

    fun updateName(
        user: FirebaseUser,
        displayName: String,
        success: () -> Unit = {},
        failure: (String?) -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (networkStatus.isOnline())
            firebaseAuth.updateName(user, displayName, success, failure)
        else
            failure.invoke(DISCONNECT_NETWORK)
    }

    fun updateEmail(
        user: FirebaseUser,
        email: String,
        success: () -> Unit = {},
        failure: (String?) -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (networkStatus.isOnline())
            firebaseAuth.updateEmail(user, email, success, failure)
        else
            failure.invoke(DISCONNECT_NETWORK)
    }

    fun updatePassword(
        user: FirebaseUser,
        password: String,
        success: () -> Unit = {},
        failure: (String?) -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (networkStatus.isOnline())
            firebaseAuth.updatePassword(user, password, success, failure)
        else
            failure.invoke(DISCONNECT_NETWORK)
    }

    fun sendResetEmail(
        email: String,
        success: () -> Unit = {},
        failure: (String?) -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (networkStatus.isOnline())
            firebaseAuth.sendResetEmail(email)
        else
            failure.invoke(DISCONNECT_NETWORK)
    }


    fun firebaseAuthWithGoogle(
        idToken: String,
        success: (FirebaseUser?) -> Unit = {},
        failure: (String?) -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (networkStatus.isOnline()) {
            firebaseAuth.firebaseAuthWithGoogle(idToken, success, failure)
        } else
            failure.invoke(DISCONNECT_NETWORK)
    }

    fun account(
        haveAccount: (FirebaseUser) -> Unit = {},
        notHaveAccount: (String?) -> Unit = {}
    ) {
        if (networkStatus.isOnline()) {
            firebaseAuth.getAccount().also {
                if (it != null)
                    haveAccount.invoke(it)
                else
                    notHaveAccount.invoke(NOT_HAVE_ACCOUNT)
            }
        } else
            notHaveAccount.invoke(null)
    }
}