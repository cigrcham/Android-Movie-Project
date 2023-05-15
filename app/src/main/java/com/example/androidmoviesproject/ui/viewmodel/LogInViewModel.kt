package com.example.androidmoviesproject.ui.viewmodel

import android.app.Activity
import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.data.model.Account
import com.example.androidmoviesproject.ui.view.LoginFragment
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor() : ViewModel() {

    fun loginAccount(
        account: Account,
        success: (FirebaseUser?) -> Unit = {},
        failure: () -> Unit = {}
    ) {
        auth.signInWithEmailAndPassword(account.userName, account.passWord)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    success.invoke(auth.currentUser)
                } else
                    failure.invoke()
            }
            .addOnFailureListener {
                failure.invoke()
            }
    }

    fun updateName(
        user: FirebaseUser,
        displayName: String,
        success: () -> Unit = {},
        failure: () -> Unit = {}
    ) {
        val profileUpdates = userProfileChangeRequest {
            setDisplayName(displayName)
        }
        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    success.invoke()
                else
                    failure.invoke()
            }
            .addOnFailureListener {
                failure.invoke()
            }
    }

    fun updateEmail(
        user: FirebaseUser,
        email: String,
        success: () -> Unit = {},
        failure: () -> Unit = {}
    ) {

        user.updateEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    success.invoke()
                else
                    failure.invoke()
            }
            .addOnFailureListener {
                failure.invoke()
            }
    }

    fun updatePassword(
        user: FirebaseUser,
        password: String,
        success: () -> Unit = {},
        failure: () -> Unit = {}
    ) {

        user.updatePassword(password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    success.invoke()
                else
                    failure.invoke()
            }
            .addOnFailureListener {
                failure.invoke()
            }
    }

    fun sendResetEmail(
        email: String
    ) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {

            }
        }
    }

    private var auth: FirebaseAuth = Firebase.auth
    fun firebaseAuthWithGoogle(
        idToken: String,
        success: () -> Unit = {},
        failure: () -> Unit = {}
    )  {
        Log.d(TAG, "firebaseAuthWithGoogle: $idToken")
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "firebaseAuthWithGoogle: ${auth.currentUser}")
                success.invoke()
            } else {
                Log.d(TAG, "firebaseAuthWithGoogle: Failure")
                failure.invoke()
            }
        }
    }

    companion object {
        private const val TAG = "GoogleActivity"
    }
}