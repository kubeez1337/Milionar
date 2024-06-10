package com.example.milionar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ScoreboardScreen(scoremanager: ScoreManager, nav: NavController, viewModel: ThemeSelectionViewModel) {
    val scoreToShow by viewModel.showScore.collectAsState()
    if (!scoreToShow) {
        viewModel.setShowScore()
    }
    val peachPink = Color(0xFFFFDAB9)
    Column(modifier = Modifier.fillMaxSize().background(peachPink)) {
        // Title
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Top 10 Rebríček",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(scoremanager.loadScores()) { index, score ->
                if (index < 10) {
                    ScoreCard(score, index + 1)
                }
            }
        }

        // Buttons Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(48.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {
                viewModel.resetScoreboard()
                viewModel.setHideScore()
            }) {
                Text(text = "Reset")
            }

            Button(onClick = { nav.navigate(MainMenu.Menu.name) }) {
                Text(text = "Menu")
            }
        }
    }
}

@Composable
fun ScoreCard(score: Score, rank: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$rank. ${score.name}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Score: ${score.score}")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                var tema = score.theme
                if (tema.equals("")){
                    tema = "Random"
                }
                Text(text = "Theme: ${tema}")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Date: ${score.date}")
            }
        }
    }
}