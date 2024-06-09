package com.example.milionar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeSelectionViewModel : ViewModel() {
    private val _selectedTheme = MutableStateFlow<String?>(null)
    private val _selectedDifficulty = MutableStateFlow<String?>(null)
    val selectedTheme: StateFlow<String?> = _selectedTheme.asStateFlow()
    val selectedDifficulty: StateFlow<String?> = _selectedDifficulty.asStateFlow()

    fun setTheme(theme: String) {
        _selectedTheme.value = theme
    }
    fun setDifficulty(diff: String) {
        _selectedDifficulty.value = diff
    }
}