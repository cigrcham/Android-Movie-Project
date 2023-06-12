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
import com.example.androidmoviesproject.broadcast.NetworkStatus
import com.example.androidmoviesproject.databinding.FragmentSplashScreenBinding
import com.example.androidmoviesproject.ui.viewmodel.LogInViewModel
import com.example.androidmoviesproject.utils.Constants.DISCONNECT_NETWORK
import com.example.androidmoviesproject.utils.Constants.NETWORK_STATUS
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class SplashScreen : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    private val viewModel: LogInViewModel by viewModels()

    @Inject
    @Named(NETWORK_STATUS)
    lateinit var networkStatus: NetworkStatus
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (networkStatus.isOnline()) {
            viewModel.account(
                haveAccount = {
                    Toast.makeText(requireContext(), "${it.displayName}", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_splashScreen_to_homeFragment)
                },
                notHaveAccount = { message:String? ->
                    Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_splashScreen_to_loginFragment)
                })
        } else {
            Toast.makeText(requireContext(), "$DISCONNECT_NETWORK", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_splashScreen_to_homeFragment)
        }
    }
}