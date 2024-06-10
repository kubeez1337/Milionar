package com.example.milionar



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

/*
TODO
OSETRENIE PO CLICKU ZLEJ ODPOVEDE - DONE
SPRAVIT LEADERBOARD Z JSONU - DONE
DUPLICITA OTAZOK - DONE
Scoreboard screen - DONE
MENU BUTTON
CASOMIERA
UI
ODOMYKANIE TEM

 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameScreen(
    viewModel: ThemeSelectionViewModel,
    onDone: () -> Unit,
    navigator: NavController
) {
    val selectedTheme by viewModel.selectedTheme.collectAsState()
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsState()
    val isCorrect by viewModel.correct.collectAsState()
    val theme by viewModel.selectedTheme.collectAsState()
    val difficulty by viewModel.selectedDifficulty.collectAsState()
    val ot = generateQuestions(theme,difficulty)
    val otazka by viewModel.selectedQuestion.collectAsState()
    val score by viewModel.score.collectAsState()
    val wasClicked by viewModel.wasclicked.collectAsState()
    val fieldToShow by viewModel.scoreField.collectAsState()
    val meno by viewModel.meno.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(60.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // pridanie paddingu pre lepší vzhľad
        ) {
            Text(
                text = otazka.question,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 20.sp,
                maxLines = Int.MAX_VALUE, // povoliť zalomenie do viacerých riadkov
                overflow = TextOverflow.Visible // zabezpečiť viditeľnosť celého textu
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Score: $score", modifier = Modifier.align(Alignment.CenterHorizontally))
        jednaOdpoved(otazka, viewModel, 0,wasClicked,isCorrect)
        jednaOdpoved(otazka, viewModel, 1,wasClicked,isCorrect)
        jednaOdpoved(otazka, viewModel, 2,wasClicked,isCorrect)
        jednaOdpoved(otazka, viewModel, 3,wasClicked,isCorrect)
        if (isCorrect) {
            viewModel.click()
            ukazButtonDalej(onDone = {
                viewModel.unclick()
                viewModel.resetQuestion() })
        } else if (wasClicked){
            if (!fieldToShow){
                ukazButtonScore(onDone = {viewModel.fieldToShow()} )
            } else {
                OutlinedTextField(value = meno,
                    onValueChange = {text -> viewModel.setMeno(text)},
                    label = { Text("Meno") })
                Spacer(modifier = Modifier.height(3.dp))
                Button(
                    onClick = {
                        viewModel.unclick()
                        viewModel.fieldToHide()
                        viewModel.saveScore(meno, score)
                        viewModel.resetScore()
                        //viewModel.resetQuestion()
                        navigator.navigate(MainMenu.Menu.name)
                    },
                    modifier = Modifier
                        //.fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Text("OK")
                }
            }
            ukazButtonSpatne(onDone = {
                navigator.navigate(MainMenu.Menu.name)
                viewModel.resetQuestion()
                viewModel.unclick()
                viewModel.resetScore()
            })
        }

    }
}


@Composable
private fun jednaOdpoved(
    otazka: Otazky,
    viewModel: ThemeSelectionViewModel,
    indexOdpovede: Int,
    clicked: Boolean,
    isCorrect: Boolean
) {

    Spacer(modifier = Modifier.height(5.dp)) // Push the button to the bottom
    Button(
        onClick = {
            if (clicked && !isCorrect || clicked && isCorrect){

            }
            else {
                verifyAnswer(
                    answer = indexOdpovede,
                    otazka = otazka,
                    viewModel = viewModel,
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
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
        //viewModel.resetScore()
        viewModel.click()
        viewModel.setIncorrect()
    }
}

@Composable
fun ukazButtonDalej(onDone: () -> Unit) {
    Spacer(modifier = Modifier.height(10.dp))
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
@Composable
fun ukazButtonScore(onDone: () -> Unit){
    Spacer(modifier = Modifier.height(16.dp))
    Row {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = onDone,
                    modifier = Modifier
                        //.fillMaxWidth()

                        .padding(40.dp)
                ) {
                    Text("Zapis score")
                }
            }
        }
    }
}
@Composable
fun ukazButtonSpatne(onDone: () -> Unit){
    Spacer(modifier = Modifier.height(20.dp))
    Row {
        Row(verticalAlignment = Alignment.CenterVertically,
            ) {
            Column {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Nespravna odpoved"
                )
            }
        }
    }

    Button(
        onClick = onDone,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Menu")
    }
}