package com.example.androidmoviesproject.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log


interface LanguageUtils {
    val context: Context

    fun setAppLocale(lang: String)
    fun dialogConfirm(invoke: () -> Unit = {})

    private inline fun savePrefLanguages(
        lang: String = LANGUAGE_VALUE["english"].toString()
    ) {
        val pref = context.getSharedPreferences(LANGUAGE_PREF, Context.MODE_PRIVATE)
        pref.edit().apply {
            putString(LANGUAGE_PREF, lang)
            apply()
        }
    }

    fun getPrefLanguages(): String {
        val pref: SharedPreferences =
            context.getSharedPreferences(LANGUAGE_PREF, Context.MODE_PRIVATE)
        val result: String? = pref.getString(LANGUAGE_PREF, null)
        return result ?: LANGUAGE_VALUE["english"].toString()
    }

    companion object {
        const val LANGUAGE_PREF: String = "share_preference_language"
        val LANGUAGE_VALUE: Map<String, String> = mapOf("english" to "en", "vietnamese" to "vi")
    }

    fun changeLangToVietnamese() {
        savePrefLanguages(lang = LANGUAGE_VALUE["vietnamese"].toString())
        dialogConfirm(invoke = {
            setAppLocale(lang = LANGUAGE_VALUE["vietnamese"].toString())
        })
    }

    fun changeLangToEnglish() {
        savePrefLanguages(lang = LANGUAGE_VALUE["english"].toString())
        dialogConfirm(invoke = {
            setAppLocale(lang = LANGUAGE_VALUE["english"].toString())
        })
    }
}
