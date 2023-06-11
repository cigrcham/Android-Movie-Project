package com.example.androidmoviesproject.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale
import javax.inject.Inject

class LanguageUtils @Inject constructor(private val context: Context) {
    private var locale: Locale = Locale("vn_")


    init {
        if (locale != Locale("")) {
            Locale.setDefault(locale)
        }
    }

    fun changeLanguage(lang: String) {
        if (!lang.isNullOrBlank()) {
            locale = Locale(lang)
            val configuration = Configuration().apply {
                setLocale(locale)
            }
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
        }
    }
}