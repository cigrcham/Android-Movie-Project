package com.example.androidmoviesproject.ui.viewmodel

import android.app.Activity
import android.content.Context
import android.provider.Settings.Global.getString
import androidx.lifecycle.ViewModel
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.data.model.Account
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor() : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
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
            if(task.isSuccessful) {

            }
        }

    }

    fun signInWithGoogle(context: Context, activity: Activity) {
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(context.getString(R.string.your_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            .build()

        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("Cigrcham")
            .requestEmail()
            .build()

        val mGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(activity, gso)


    }


}