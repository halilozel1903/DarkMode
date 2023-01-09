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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binding.changeTheme = this
        checkToTheme()
    }

    fun chooseThemeDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle(getString(R.string.choose_theme_text))
        val themes = arrayOf(LIGHT, DARK, DEFAULT)
        val checkedItem = MyPreferences(this@MainActivity).darkMode

        builder.setSingleChoiceItems(themes, checkedItem) { dialog, which ->
            when (which) {
                ZERO -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(this@MainActivity).darkMode = ZERO
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                ONE -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(this@MainActivity).darkMode = ONE
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                TWO -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(this@MainActivity).darkMode = TWO
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
            }
        }
         builder.create().apply {
            show()
        }
    }


    private fun checkToTheme() {
        when (MyPreferences(this@MainActivity).darkMode) {
            ZERO -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            ONE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            TWO -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }
    }

    companion object {
        private const val LIGHT = "Light Theme"
        private const val DARK = "Dark Theme"
        private const val DEFAULT = "System Default Theme"
    }
}