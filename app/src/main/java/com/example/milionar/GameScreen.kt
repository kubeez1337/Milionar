package com.example.milionar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun GameScreen(
    viewModel: ThemeSelectionViewModel,
    onDone: () -> Unit// Callback to navigate back
) {
    val selectedTheme by viewModel.selectedTheme.collectAsState()
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsState()
    val isCorrect by viewModel.correct.collectAsState()
    val ot = generateQuestions()
    val otazka by viewModel.selectedQuestion.collectAsState()
    val score by viewModel.score.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(otazka.question)
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Score: $score")
        jednaOdpoved(otazka, viewModel, 0)
        jednaOdpoved(otazka, viewModel, 1)
        jednaOdpoved(otazka, viewModel, 2)
        jednaOdpoved(otazka, viewModel, 3)
        if (isCorrect) {
            ukazButtonDalej(onDone = { viewModel.resetQuestion() })
        }

    }
}


@Composable
private fun jednaOdpoved(
    otazka: Otazky,
    viewModel: ThemeSelectionViewModel,
    indexOdpovede: Int
) {
    Spacer(modifier = Modifier.height(10.dp)) // Push the button to the bottom
    Button(
        onClick = {
            verifyAnswer(
                answer = indexOdpovede,
                otazka = otazka,
                viewModel = viewModel,
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(otazka.options.get(indexOdpovede))
    }
}

fun verifyAnswer(
    answer: Int,
    otazka: Otazky,
    viewModel: ThemeSelectionViewModel,
) {
    if (answer == otazka.correctAnswer) {
        viewModel.incrementScore()
        viewModel.setCorrect()
    } else {
        viewModel.resetScore()
        viewModel.setIncorrect()
    }
}

@Composable
fun ukazButtonDalej(onDone: () -> Unit) {
    Spacer(modifier = Modifier.height(120.dp))
    Row {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Spravna odpoved!")
        }
    }
    Button(
        onClick = onDone,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Dalsi")
    }
}