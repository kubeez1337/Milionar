package com.example.milionar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _selectedDifficulty = MutableLiveData<String?>(null)
    val selectedDifficulty: LiveData<String?> = _selectedDifficulty

    private val _selectedLevel = MutableLiveData<Int?>(null)
    val selectedLevel: LiveData<Int?> = _selectedLevel

    fun setDifficulty(difficulty: String) {
        _selectedDifficulty.value = difficulty
    }

    fun setLevel(level: Int) {
        _selectedLevel.value = level
    }
}