package com.example.androidmoviesproject.ui

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    private fun setUp() {
        val window: Window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.top_color)
    }

    fun setAppLocale(context: Context, lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        val contextThemeWrapper =
            ContextThemeWrapper(context, R.style.Base_Theme_AndroidMoviesProject)
        contextThemeWrapper.createConfigurationContext(config)
        contextThemeWrapper.resources.updateConfiguration(
            config, contextThemeWrapper.resources.displayMetrics
        )
    }
}