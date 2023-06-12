package com.example.androidmoviesproject.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.Locale

object LanguageHelper {
    fun wrapContext(context: Context): Context {
        val locale = Locale(getSavedLanguage(context))
        Locale.setDefault(locale)

        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

    fun applyLanguage(context: Context) {
        val locale = Locale(getSavedLanguage(context))
        Locale.setDefault(locale)

        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }

    private fun getSavedLanguage(context: Context): String {
        // Retrieve the saved language from your preference or other storage mechanism
        // You can replace this with your own logic to get the saved language
        // This example uses a shared preference
        val sharedPreferences =
            context.getSharedPreferences("LanguagePreference", Context.MODE_PRIVATE)
        return sharedPreferences.getString("language", "en") ?: "en"
    }
}