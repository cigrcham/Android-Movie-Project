package com.example.androidmoviesproject.data.firebase

import com.example.androidmoviesproject.data.model.Account
import com.example.androidmoviesproject.utils.Constants.CREATE_ACCOUNT_FAILURE
import com.example.androidmoviesproject.utils.Constants.CREATE_ACCOUNT_SUCCESS
import com.example.androidmoviesproject.utils.Constants.FIREBASE_AUTH_KEY
import com.example.androidmoviesproject.utils.Constants.LOGIN_FAILURE
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Named

class FirebaseAuthenticationImpl @Inject constructor(
    @Named(FIREBASE_AUTH_KEY)
    private val auth: FirebaseAuth
) : FirebaseAuthentication {
    override fun loginAccount(
        account: Account,
        success: (FirebaseUser?) -> Unit,
        failure: (String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(account.userName, account.passWord)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    success.invoke(auth.currentUser)
                } else {
                    failure.invoke(LOGIN_FAILURE)
                }
            }
            .addOnFailureListener {
                failure.invoke(LOGIN_FAILURE)
            }
    }

    override fun updateName(
        user: FirebaseUser,
        displayName: String,
        success: () -> Unit,
        failure: (String?) -> Unit
    ) {
        val profileUpdates = userProfileChangeRequest {
            setDisplayName(displayName)
        }
        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    success.invoke()
                else
                    failure.invoke(null)
            }
    }

    override fun updateEmail(
        user: FirebaseUser,
        email: String,
        success: () -> Unit,
        failure: (String?) -> Unit
    ) {
        user.updateEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful)
                success.invoke()
            else
                failure.invoke(null)
        }.addOnFailureListener {
            failure.invoke(null)
        }
    }

    override fun updatePassword(
        user: FirebaseUser,
        password: String,
        success: () -> Unit,
        failure: (String?) -> Unit
    ) {
        user.updatePassword(password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    success.invoke()
                else
                    failure.invoke(null)
            }.addOnFailureListener {
                failure.invoke(null)
            }
    }

    override fun sendResetEmail(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {

            } else {

            }
        }
    }

    override fun firebaseAuthWithGoogle(
        idToken: String,
        success: (FirebaseUser?) -> Unit,
        failure: (String?) -> Unit
    ) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                success.invoke(auth.currentUser)
            } else {
                failure.invoke(null)
            }
        }
    }

    override fun createAccount(
        account: Account,
        success: (String?) -> Unit,
        failure: (String?) -> Unit
    ) {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(account.userName, account.passWord)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    success.invoke(CREATE_ACCOUNT_SUCCESS)
                } else {
                    failure.invoke(CREATE_ACCOUNT_FAILURE)
                }
            }
            .addOnFailureListener {
                failure.invoke(CREATE_ACCOUNT_FAILURE)
            }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getAccount(): FirebaseUser? {
        return auth.currentUser
    }
}