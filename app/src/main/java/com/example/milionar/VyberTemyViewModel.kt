package com.example.milionar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class ThemeSelectionViewModel(scoreManager: ScoreManager) : ViewModel() {
    private val _selectedTheme = MutableStateFlow<String>("")
    private val _selectedDifficulty = MutableStateFlow<String>("")
    val selectedTheme: StateFlow<String> = _selectedTheme.asStateFlow()
    val selectedDifficulty: StateFlow<String> = _selectedDifficulty.asStateFlow()
    private val _score = MutableStateFlow<Int>(0)
    val score: StateFlow<Int> = _score.asStateFlow()
    private val _correct = MutableStateFlow<Boolean>(false)
    val correct: StateFlow<Boolean> = _correct.asStateFlow()
    private val _wasclicked = MutableStateFlow<Boolean>(false)
    val wasclicked: StateFlow<Boolean> = _wasclicked.asStateFlow()
    var ot = generateQuestions(selectedTheme.value,selectedDifficulty.value).shuffled()
    private val _selectedQuestion = MutableStateFlow<Otazky>(ot.get(0))
    var selectedQuestion: StateFlow<Otazky> = _selectedQuestion.asStateFlow()
    var indexOtazky = 0
    private val _scoreField = MutableStateFlow<Boolean>(false)
    val scoreField: StateFlow<Boolean> = _scoreField.asStateFlow()
    private val _meno = MutableStateFlow<String>("")
    val meno : StateFlow<String> = _meno.asStateFlow()
    val scoreboard = scoreManager

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
    fun setQuestions(){
        ot = generateQuestions(selectedTheme.value,selectedDifficulty.value).shuffled()
        indexOtazky = 0
        _selectedQuestion.value = ot.get(indexOtazky)
    }
    fun resetQuestion(){
        ++indexOtazky
        if (indexOtazky == ot.size){
            ot = generateQuestions(selectedTheme.value,selectedDifficulty.value).shuffled()
            indexOtazky = 0
        }
        setIncorrect()
        if (ot.get(indexOtazky) == _selectedQuestion.value){
            resetQuestion()
            return
        }
        _selectedQuestion.value = ot.get(indexOtazky)
    }
    fun resetScore(){
        _score.value = 0
    }
    fun click(){
        _wasclicked.value = true
    }
    fun unclick(){
        _wasclicked.value = false
    }
    fun fieldToShow(){
        _scoreField.value = true
    }
    fun fieldToHide(){
        _scoreField.value = false
    }
    fun setMeno(meno: String){
        _meno.value = meno
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveScore(meno: String, score: Int){
        val formatt = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")
        scoreboard.saveScore(Score(meno, score, LocalDateTime.now().format(formatt), selectedTheme.value))
        setMeno("")
    }

}