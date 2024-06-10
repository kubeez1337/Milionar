package com.example.milionar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ScoreboardScreen(scoremanager: ScoreManager, nav : NavController, viewModel: ThemeSelectionViewModel) {
    val scoreToShow by viewModel.showScore.collectAsState()
    showScoreBoard(scores = scoremanager.loadScores())
    if (!scoreToShow){
        showScoreBoard(scores = scoremanager.loadScores())
        viewModel.setShowScore()
    }
    Row {
        Button(onClick = {
            viewModel.resetScoreboard()
            viewModel.setHideScore()
        }) {
            Text(text = "Reset")
        }
        Spacer(modifier = Modifier.width(150.dp))
        Button(onClick = { nav.navigate(MainMenu.Menu.name) }) {
            Text(text = "Menu")
        }
    }
}
@Composable
fun showScoreBoard(scores: List<Score>){
    Spacer(modifier = Modifier.height(150.dp))
    Column {
        Spacer(modifier = Modifier.height(30.dp))
        Text("Top 10 Scores")
        Spacer(modifier = Modifier.height(8.dp))
        for (index in scores.indices) {
            if (index < 10) {
                val score = scores[index]
                Text("${index + 1}. ${score.name}: ${score.score}, ${score.theme}, \t${score.date}")
            }
        }
    }

}

    

