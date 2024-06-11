package com.example.milionar.ViewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DifficultyViewModel : ViewModel() {
    private val _selectedDifficulty = MutableStateFlow<String>("")
    val selectedDifficulty: StateFlow<String> = _selectedDifficulty.asStateFlow()

    fun setDifficulty(difficulty: String) {
        _selectedDifficulty.value = difficulty
    }
}