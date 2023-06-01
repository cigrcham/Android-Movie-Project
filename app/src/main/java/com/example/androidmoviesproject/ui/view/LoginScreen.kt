package com.example.androidmoviesproject.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.broadcast.NetworkStatus
import com.example.androidmoviesproject.data.model.Account
import com.example.androidmoviesproject.databinding.FragmentLoginBinding
import com.example.androidmoviesproject.ui.viewmodel.LogInViewModel
import com.example.androidmoviesproject.utils.Constants.DISCONNECT_NETWORK
import com.example.androidmoviesproject.utils.Constants.NETWORK_STATUS
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class LoginScreen : Fragment() {
    @Inject
    @Named(NETWORK_STATUS)
    lateinit var networkStatus: NetworkStatus
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LogInViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                viewModel.firebaseAuthWithGoogle(account.idToken!!, success = {
                    if (it != null) {
                        statusLogin(check = true, it.displayName)
                    }
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }, failure = { message: String? ->
                    Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
                })
            } catch (e: ApiException) {
                statusLogin(false, null)
            }
        }
    }

    private fun statusLogin(check: Boolean, string: String? = null) {
        var status: String = if (check) "Login Success" else "Login Failure"

        if (string != null) {
            status += " $string"
        }
        Toast.makeText(requireContext(), "$status", Toast.LENGTH_SHORT).show()
    }

    private fun signInWithGoogle() {
        if (networkStatus.isOnline()) {
            val signIntent = googleSignInClient.signInIntent
            startActivityForResult(signIntent, RC_SIGN_IN)
        } else {
            Toast.makeText(requireContext(), "$DISCONNECT_NETWORK", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        binding.btnGoogle.setOnClickListener {
            isOnline(online = {
                signInWithGoogle()
            })
        }
        binding.goToSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signInFragment)
        }
        buttonEnable()
        binding.inputAccount.addTextChangedListener {
            buttonEnable()
        }
        binding.inputPassword.addTextChangedListener {
            buttonEnable()
        }
        binding.btnLogin.setOnClickListener {
            isOnline(online = {
                val account = Account(
                    binding.inputAccount.text.toString(),
                    binding.inputPassword.text.toString()
                )
                viewModel.loginAccount(account = account, success = {
                    if (it != null) {
                        statusLogin(true, it.displayName)
                    }
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }, failure = {
                    statusLogin(false)
                })
            })
        }
    }

    private fun buttonEnable() {
        val check = !(binding.inputAccount.text.toString()
            .isNullOrEmpty() || binding.inputPassword.text.toString().isNullOrEmpty())
        binding.btnLogin.isEnabled = check
        val color = if (check) {
            ContextCompat.getColor(requireContext(), R.color.button_login)
        } else {
            ContextCompat.getColor(requireContext(), R.color.gray_color)
        }
        binding.btnLogin.setBackgroundColor(color)
    }

    private inline fun isOnline(
        crossinline online: () -> Unit = {},
        crossinline offline: () -> Unit = {
            Toast.makeText(requireContext(), "$DISCONNECT_NETWORK", Toast.LENGTH_SHORT).show()
        }
    ) {
        if (networkStatus.isOnline()) {
            online.invoke()
        } else
            offline.invoke()
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}