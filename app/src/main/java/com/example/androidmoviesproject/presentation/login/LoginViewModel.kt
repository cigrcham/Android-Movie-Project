package com.example.androidmoviesproject.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.data.firebase.FirebaseAuthentication
import com.example.androidmoviesproject.data.model.Account
import com.example.androidmoviesproject.utils.Constants.NOT_HAVE_ACCOUNT
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuthentication,
) : ViewModel() {
    fun loginAccount(
        account: Account,
        success: (FirebaseUser?) -> Unit = {},
        failure: (String?) -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        firebaseAuth.loginAccount(account, success, failure)
    }

    fun updateName(
        user: FirebaseUser,
        displayName: String,
        success: () -> Unit = {},
        failure: (String?) -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        firebaseAuth.updateName(user, displayName, success, failure)
    }

    fun updateEmail(
        user: FirebaseUser,
        email: String,
        success: () -> Unit = {},
        failure: (String?) -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        firebaseAuth.updateEmail(user, email, success, failure)
    }

    fun updatePassword(
        user: FirebaseUser,
        password: String,
        success: () -> Unit = {},
        failure: (String?) -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        firebaseAuth.updatePassword(user, password, success, failure)
    }

    fun sendResetEmail(
        email: String,
        success: () -> Unit = {},
        failure: (String?) -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        firebaseAuth.sendResetEmail(email)
    }


    fun firebaseAuthWithGoogle(
        idToken: String,
        success: (FirebaseUser?) -> Unit = {},
        failure: (String?) -> Unit = {}
    ) = viewModelScope.launch(Dispatchers.IO) {
        firebaseAuth.firebaseAuthWithGoogle(idToken, success, failure)
    }

    fun account(
        haveAccount: (FirebaseUser) -> Unit = {},
        notHaveAccount: (String?) -> Unit = {}
    ) {
        firebaseAuth.getAccount().also {
            if (it != null)
                haveAccount.invoke(it)
            else
                notHaveAccount.invoke(NOT_HAVE_ACCOUNT)
        }
    }
}