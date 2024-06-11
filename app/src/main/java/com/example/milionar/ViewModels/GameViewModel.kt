package com.example.milionar.ViewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.milionar.DataManagement.Otazka
import com.example.milionar.DataManagement.Otazky
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel(vsetkyOtazky: Otazka,
                    themeViewModel: ThemeSelectionViewModel,
                    difficultyViewModel: DifficultyViewModel,
                    scoreboardViewModel: ScoreboardViewModel) : ViewModel() {
    val scoreboardViewModel = scoreboardViewModel
    val selectedTheme = themeViewModel.selectedTheme
    val selectedDifficulty = difficultyViewModel.selectedDifficulty
    private val _score = MutableStateFlow<Int>(0)
    val score: StateFlow<Int> = _score.asStateFlow()
    private val _correct = MutableStateFlow<Boolean>(false)
    val correct: StateFlow<Boolean> = _correct.asStateFlow()
    private val _wasclicked = MutableStateFlow<Boolean>(false)
    val wasclicked: StateFlow<Boolean> = _wasclicked.asStateFlow()
    val vsetkyOtazky = vsetkyOtazky
    var ot = vsetkyOtazky.generateQuestions(selectedTheme.value,selectedDifficulty.value).shuffled()
    private val _selectedQuestion = MutableStateFlow<Otazky>(ot.get(0))
    var selectedQuestion: StateFlow<Otazky> = _selectedQuestion.asStateFlow()
    var indexOtazky = 0
    private val _isTimerRunning = MutableStateFlow<Boolean>(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()
    private val _timeRemaining = MutableStateFlow<Int>(15)
    val timeRemaining: StateFlow<Int> = _timeRemaining.asStateFlow()
    private val _scoreField = MutableStateFlow<Boolean>(false)
    val scoreField: StateFlow<Boolean> = _scoreField.asStateFlow()
    private val _meno = MutableStateFlow<String>("")
    val meno : StateFlow<String> = _meno.asStateFlow()

    fun incrementScore() {
        _score.value++
    }
    fun resetScore(){
        _score.value = 0
    }
    fun setCorrect() {
        _correct.value = true
    }
    fun setIncorrect() {
        _correct.value = false
    }
    fun setQuestions(){
        ot = vsetkyOtazky.generateQuestions(selectedTheme.value,selectedDifficulty.value).shuffled()
        indexOtazky = 0
        _selectedQuestion.value = ot.get(indexOtazky)
    }
    fun resetQuestion(){
        ++indexOtazky
        if (indexOtazky == ot.size){
            ot = vsetkyOtazky.generateQuestions(selectedTheme.value,selectedDifficulty.value).shuffled()
            indexOtazky = 0
        }
        setIncorrect()
        if (ot.get(indexOtazky) == _selectedQuestion.value){
            resetQuestion()
            return
        }
        _selectedQuestion.value = ot.get(indexOtazky)
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
        scoreboardViewModel.saveScore(meno, score, selectedTheme.value)
        setMeno("")
    }
    fun setTimerRunning(bool: Boolean){
        _isTimerRunning.value = bool
    }
    fun resetTimeRemaining(){
        _timeRemaining.value = 15
    }
    fun decrementTimeRemaining(){
        _timeRemaining.value--
    }
}