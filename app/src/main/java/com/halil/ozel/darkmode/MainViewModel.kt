package com.halil.ozel.darkmode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ThemeUiState(
    val selectedTheme: ThemeMode = ThemeMode.System,
    val options: List<ThemeMode> = ThemeMode.entries,
)

class MainViewModel(
    private val themePreferences: ThemePreferences,
) : ViewModel() {

    val uiState: StateFlow<ThemeUiState> = themePreferences.themeMode
        .map { selectedTheme -> ThemeUiState(selectedTheme = selectedTheme) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = ThemeUiState(),
        )

    fun selectTheme(themeMode: ThemeMode) {
        viewModelScope.launch {
            themePreferences.setThemeMode(themeMode)
        }
    }

    class Factory(
        private val themePreferences: ThemePreferences,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(themePreferences) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
