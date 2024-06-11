package com.example.milionar.ViewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.milionar.DataManagement.Score
import com.example.milionar.DataManagement.ScoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ScoreboardViewModel(scoreManager : ScoreManager): ViewModel() {
    private val _showScore = MutableStateFlow<Boolean>(true)
    val showScore: StateFlow<Boolean> = _showScore.asStateFlow()
    val scoreboard = scoreManager

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveScore(meno: String, score: Int, selectedTheme : String){
        val formatt = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")
        scoreboard.saveScore(Score(meno, score, LocalDateTime.now().format(formatt), selectedTheme))
    }
    fun setShowScore(){
        _showScore.value = true
    }
    fun setHideScore(){
        _showScore.value = false
    }
    fun resetScoreboard(){
        scoreboard.resetScoreboard()
    }

}