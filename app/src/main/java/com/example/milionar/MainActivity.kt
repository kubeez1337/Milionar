package com.example.milionar

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.milionar.DataManagement.Otazka
import com.example.milionar.DataManagement.ScoreManager
import com.example.milionar.Screens.MilionarApp
import com.example.milionar.ViewModels.DifficultyViewModel
import com.example.milionar.ViewModels.GameViewModel
import com.example.milionar.ViewModels.ScoreboardViewModel
import com.example.milionar.ViewModels.ThemeSelectionViewModel
import com.example.milionar.ui.theme.MilionarTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val scoreManager = ScoreManager(this)
        val otazky = Otazka(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MilionarTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val themeViewModel = ThemeSelectionViewModel()
                    val difficultyViewModel = DifficultyViewModel()
                    val scoreboardViewModel = ScoreboardViewModel(scoreManager)
                    val gameViewModel = GameViewModel(
                        otazky,
                        themeViewModel,
                        difficultyViewModel,
                        scoreboardViewModel
                    )
                    MilionarApp(
                        themeViewModel,
                        gameViewModel,
                        difficultyViewModel,
                        scoreboardViewModel
                    )
                }
            }
        }
    }
}




