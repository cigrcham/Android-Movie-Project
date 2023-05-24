package com.example.androidmoviesproject.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.broadcast.NetworkStatus
import com.example.androidmoviesproject.data.model.Account
import com.example.androidmoviesproject.databinding.FragmentSignUpBinding
import com.example.androidmoviesproject.ui.viewmodel.SignUpViewModel
import com.example.androidmoviesproject.utils.Constants
import com.example.androidmoviesproject.utils.Constants.NETWORK_STATUS
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class SignUpScreen : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    @Inject
    @Named(NETWORK_STATUS)
    lateinit var networkStatus: NetworkStatus

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
        }
        buttonEnable()
        binding.inputPassword.addTextChangedListener {
            buttonEnable()
        }
        binding.inputRePassword.addTextChangedListener {
            buttonEnable()
        }
        binding.inputAccount.addTextChangedListener {
            buttonEnable()
        }
        binding.btnSignUn.setOnClickListener {
            isOnline(online = {
                if (binding.inputPassword.text.toString() != binding.inputRePassword.text.toString()) {
                    Toast.makeText(
                        requireContext(),
                        "Password and Re-Password is different",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val account = Account(
                        binding.inputAccount.text.toString(),
                        binding.inputPassword.text.toString()
                    )
                    viewModel.createAccount(
                        account,
                        success = { message: String? ->
                            Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
                        },
                        failure = { message: String? ->
                            Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
                        })
                }
            })
        }
    }

    private inline fun isOnline(
        crossinline online: () -> Unit = {},
        crossinline offline: () -> Unit = {
            Toast.makeText(requireContext(), "${Constants.DISCONNECT_NETWORK}", Toast.LENGTH_SHORT)
                .show()
        }
    ) {
        if (networkStatus.isOnline()) {
            online.invoke()
        } else
            offline.invoke()
    }

    private fun buttonEnable() {
        val check: Boolean = !(binding.inputAccount.text.toString()
            .isNullOrEmpty() || binding.inputPassword.text.toString()
            .isNullOrEmpty() || binding.inputRePassword.text.toString().isNullOrEmpty())
        binding.btnSignUn.isEnabled = check
        val color = if (check) {
            ContextCompat.getColor(requireContext(), R.color.button_login)
        } else {
            ContextCompat.getColor(requireContext(), R.color.gray_color)
        }
        binding.btnSignUn.setBackgroundColor(color)
    }
}