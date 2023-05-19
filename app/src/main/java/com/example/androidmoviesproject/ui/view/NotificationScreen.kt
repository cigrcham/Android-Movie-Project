package com.example.androidmoviesproject.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.adapter.notification.NotificationAdapter
import com.example.androidmoviesproject.databinding.FragmentNotificationScreenBinding
import com.example.androidmoviesproject.ui.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationScreen : Fragment() {
    private lateinit var binding: FragmentNotificationScreenBinding
    private val viewModel: NotificationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolBar()
        setUpRecycleView()
    }

    private fun setUpRecycleView() {
        val adapter = NotificationAdapter()
        binding.recycleNotification.adapter = adapter
        binding.recycleNotification.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        lifecycleScope.launch {
            viewModel.notifyData().observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    private fun setUpToolBar() {
        binding.backNavigation.setOnClickListener {
            findNavController().navigate(R.id.action_notificationScreen_to_homeFragment)
        }
    }
}