package com.example.androidmoviesproject.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.databinding.FragmentSplashScreenBinding
import com.example.androidmoviesproject.ui.viewmodel.LogInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreen : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    private val viewModel: LogInViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.account(
            haveAccount = {
                Toast.makeText(requireContext(), "${it.displayName}", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_splashScreen_to_homeFragment)
            },
            notHaveAccount = { message: String? ->
//                if (message != null)
//                    Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_splashScreen_to_loginFragment)
            })
    }
}