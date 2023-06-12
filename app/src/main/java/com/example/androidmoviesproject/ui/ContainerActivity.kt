package com.example.androidmoviesproject.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.example.androidmoviesproject.R
import com.example.androidmoviesproject.databinding.ActivityContainerBinding
import com.example.androidmoviesproject.ui.DialogConfirm.Companion.TAG_DIALOG_CONFIRM
import com.example.androidmoviesproject.utils.LanguageUtils
import com.example.androidmoviesproject.utils.LanguageUtils.Companion.LANGUAGE_PREF
import com.example.androidmoviesproject.utils.LanguageUtils.Companion.LANGUAGE_VALUE
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class ContainerActivity @Inject constructor(
//    @Named(LANGUAGE_PREF) private val languageUtils: LanguageUtils
) : AppCompatActivity(), LanguageUtils {
    private lateinit var binding: ActivityContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
        this.setAppLocale(this.getPrefLanguages())
    }

    private fun setUp() {
        val window: Window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.top_color)
    }

    override fun setAppLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        val contextThemeWrapper =
            ContextThemeWrapper(baseContext, R.style.Base_Theme_AndroidMoviesProject)
        contextThemeWrapper.createConfigurationContext(config)
        contextThemeWrapper.resources.updateConfiguration(
            config, contextThemeWrapper.resources.displayMetrics
        )
    }

    override fun dialogConfirm(invoke: () -> Unit) =
        DialogConfirm(title = getString(R.string.title_lang),
            message = getString(R.string.message_lang),
            onPositive = {
                invoke.invoke()
                restartApp()
            }).show(
            supportFragmentManager, TAG_DIALOG_CONFIRM
        )

    override val context: Context
        get() = applicationContext

    private fun restartApp() {
        val intent = this.packageManager.getLaunchIntentForPackage(this.packageName);
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
        }
    }
}