package com.halil.ozel.darkmode

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.halil.ozel.darkmode.Numbers.ONE
import com.halil.ozel.darkmode.Numbers.TWO
import com.halil.ozel.darkmode.Numbers.ZERO
import com.halil.ozel.darkmode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val preferences by lazy { MyPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.changeTheme = this
        applySavedTheme()
        updateCurrentThemeText(preferences.darkMode)
    }

    fun chooseThemeDialog() {
        val themeLabels = themeLabels()
        val checkedItem = preferences.darkMode

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.choose_theme_text))
            .setSingleChoiceItems(themeLabels, checkedItem) { dialog, selectedTheme ->
                applyTheme(selectedTheme)
                dialog.dismiss()
            }
            .show()
    }

    private fun applySavedTheme() {
        applyTheme(preferences.darkMode, shouldPersist = false)
    }

    private fun applyTheme(theme: Int, shouldPersist: Boolean = true) {
        AppCompatDelegate.setDefaultNightMode(theme.toNightMode())
        if (shouldPersist) {
            preferences.darkMode = theme
        }
        updateCurrentThemeText(theme)
        delegate.applyDayNight()
    }

    private fun updateCurrentThemeText(theme: Int) {
        val label = themeLabels().getOrElse(theme) { getString(R.string.theme_system) }
        binding.txtCurrentTheme.text = getString(R.string.current_theme_label, label)
    }

    private fun themeLabels(): Array<String> = arrayOf(
        getString(R.string.theme_light),
        getString(R.string.theme_dark),
        getString(R.string.theme_system)
    )

    private fun Int.toNightMode(): Int = when (this) {
        ZERO -> AppCompatDelegate.MODE_NIGHT_NO
        ONE -> AppCompatDelegate.MODE_NIGHT_YES
        TWO -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }
}
