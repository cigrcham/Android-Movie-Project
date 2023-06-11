package com.example.androidmoviesproject.utils

import java.util.Locale


class LanguageUtils {
    private var locale: Locale? = null

    init {
        if (locale != Locale("")) Locale.setDefault(locale)
    }


    companion object {
        const val LANGUAGE_MODULE: String = "Feature_Language"
    }
}