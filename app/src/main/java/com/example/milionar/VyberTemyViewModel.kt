package com.example.milionar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class ThemeSelectionViewModel : ViewModel() {
    private val _selectedTheme = MutableStateFlow<String?>(null)
    private val _selectedDifficulty = MutableStateFlow<String?>(null)
    val selectedTheme: StateFlow<String?> = _selectedTheme.asStateFlow()
    val selectedDifficulty: StateFlow<String?> = _selectedDifficulty.asStateFlow()
    private val _score = MutableStateFlow<Int>(0)
    val score: StateFlow<Int> = _score.asStateFlow()
    private val _correct = MutableStateFlow<Boolean>(false)
    val correct: StateFlow<Boolean> = _correct.asStateFlow()
    var ot = generateQuestions().shuffled()
    private val _selectedQuestion = MutableStateFlow<Otazky>(ot.get(0))
    var selectedQuestion: StateFlow<Otazky> = _selectedQuestion.asStateFlow()
    var indexOtazky = 0
    fun setTheme(theme: String) {
        _selectedTheme.value = theme
    }
    fun setDifficulty(diff: String) {
        _selectedDifficulty.value = diff
    }
    fun incrementScore() {
        _score.value++
    }
    fun setCorrect() {
        _correct.value = true
    }
    fun setIncorrect() {
        _correct.value = false
    }
    fun resetQuestion(){
        ++indexOtazky
        if (indexOtazky == ot.size){
            ot = generateQuestions().shuffled()
            indexOtazky = 0
        }
        setIncorrect()
        _selectedQuestion.value = ot.get(indexOtazky)
    }
    fun resetScore(){
        _score.value = 0
    }

}