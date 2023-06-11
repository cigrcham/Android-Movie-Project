package com.example.androidmoviesproject.ui

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.databinding.ActivityContainerBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class ContainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContainerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
    }

    init {
        updateConfig(this)
    }
    fun updateConfig(wrapper: ContextThemeWrapper) {
//        if(dLocale== Locale("") ) // Do nothing if dLocale is null
//            return
        dLocale = Locale("vi")
        Locale.setDefault(dLocale)
        val configuration = Configuration()
        configuration.setLocale(dLocale)
        wrapper.applyOverrideConfiguration(configuration)
    }

    private fun setUp() {
        val window: Window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.top_color)
    }

    private var dLocale: Locale? = null

}