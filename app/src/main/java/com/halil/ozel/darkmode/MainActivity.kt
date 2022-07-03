package com.halil.ozel.darkmode

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.halil.ozel.darkmode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.changeTheme = this
        checkToTheme()
    }

    fun chooseThemeDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.choose_theme_text))
        val themes = arrayOf(LIGHT, DARK, DEFAULT)
        val checkedItem = MyPreferences(this).darkMode

        builder.setSingleChoiceItems(themes, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(this).darkMode = 0
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(this).darkMode = 1
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(this).darkMode = 2
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

            }
        }
        val dialog = builder.create()
        dialog.show()
    }


    private fun checkToTheme() {
        when (MyPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }
    }

    companion object {
        const val LIGHT = "Light Theme"
        const val DARK = "Dark Theme"
        const val DEFAULT = "System Default Theme"
    }
}