package com.example.androidmoviesproject.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.androidmoviesproject.data.model.Account
import com.example.androidmoviesproject.databinding.FragmentLoginBinding
import com.example.androidmoviesproject.ui.viewmodel.LogInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LogInViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult: $data")
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "onActivityResult: ${account.idToken!!}")
                viewModel.firebaseAuthWithGoogle(account.idToken!!, success = {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                })
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
    private fun signIn() {
        val signIntent = googleSignInClient.signInIntent
        startActivityForResult(signIntent, RC_SIGN_IN)
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
        binding.btnGoogle.setOnClickListener {
            signIn()
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
            val account = Account(
                binding.inputAccount.text.toString(),
                binding.inputPassword.text.toString()
            )
            viewModel.loginAccount(account = account, success = {
                if (it != null) {
                    Toast.makeText(requireContext(), "${it.email}", Toast.LENGTH_SHORT).show()
                }
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }, failure = {
                Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show()
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


}