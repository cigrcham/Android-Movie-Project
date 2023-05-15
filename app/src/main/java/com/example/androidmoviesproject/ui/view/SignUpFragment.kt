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
import com.example.androidmoviesproject.data.model.Account
import com.example.androidmoviesproject.databinding.FragmentSignUpBinding
import com.example.androidmoviesproject.ui.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

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
        binding.btnSignIn.setOnClickListener {
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
                viewModel.createAccount(account, success = {
                    Toast.makeText(requireContext(), "Sign Up Success", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
                }, failure = {
                    Toast.makeText(requireContext(), "Sign Up Error", Toast.LENGTH_SHORT).show()
                })
            }

        }
    }

    private fun buttonEnable() {
        val check: Boolean = !(binding.inputAccount.text.toString()
            .isNullOrEmpty() || binding.inputPassword.text.toString()
            .isNullOrEmpty() || binding.inputRePassword.text.toString().isNullOrEmpty())
        binding.btnSignIn.isEnabled = check
        val color = if (check) {
            ContextCompat.getColor(requireContext(), R.color.button_login)
        } else {
            ContextCompat.getColor(requireContext(), R.color.gray_color)
        }
        binding.btnSignIn.setBackgroundColor(color)
    }
}