package com.example.androidmoviesproject.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.data.model.Account
import com.example.androidmoviesproject.data.model.ModelMovie
import com.example.androidmoviesproject.databinding.FragmentLoginBinding
import com.example.androidmoviesproject.ui.viewmodel.LogInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LogInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.signInWithGoogle(requireContext(), requireActivity())

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