package com.example.androidmoviesproject.data.firebase

import com.example.androidmoviesproject.data.model.Account
import com.example.androidmoviesproject.utils.Constants.FIREBASE_AUTH_KEY
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
        failure: () -> Unit
    ) {
        auth.signInWithEmailAndPassword(account.userName, account.passWord)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    success.invoke(auth.currentUser)
                } else {
                    failure.invoke()
                }
            }
            .addOnFailureListener {
                failure.invoke()
            }
    }

    override fun updateName(
        user: FirebaseUser,
        displayName: String,
        success: () -> Unit,
        failure: () -> Unit
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
    }

    override fun updateEmail(
        user: FirebaseUser,
        email: String,
        success: () -> Unit,
        failure: () -> Unit
    ) {
        user.updateEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful)
                success.invoke()
            else
                failure.invoke()
        }.addOnFailureListener {
            failure.invoke()
        }
    }

    override fun updatePassword(
        user: FirebaseUser,
        password: String,
        success: () -> Unit,
        failure: () -> Unit
    ) {
        user.updatePassword(password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    success.invoke()
                else
                    failure.invoke()
            }.addOnFailureListener {
                failure.invoke()
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
        failure: () -> Unit
    ) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                success.invoke(auth.currentUser)
            } else {
                failure.invoke()
            }
        }
    }

    override fun createAccount(account: Account, success: () -> Unit, failure: () -> Unit) {
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

    override fun signOut() {
        auth.signOut()
    }

    override fun getAccount(): FirebaseUser? {
        return auth.currentUser
    }
}