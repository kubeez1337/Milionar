package com.example.milionar.ViewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.milionar.DataManagement.Otazka
import com.example.milionar.DataManagement.Otazky
import com.example.milionar.DataManagement.Score
import com.example.milionar.DataManagement.ScoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ThemeSelectionViewModel() : ViewModel() {
    private val _selectedTheme = MutableStateFlow<String>("")
    val selectedTheme: StateFlow<String> = _selectedTheme.asStateFlow()
    fun setTheme(theme: String) {
        _selectedTheme.value = theme
    }

}