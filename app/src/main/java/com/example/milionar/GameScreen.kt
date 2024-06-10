package com.example.milionar



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

/*
TODO
OSETRENIE PO CLICKU ZLEJ ODPOVEDE - DONE
SPRAVIT LEADERBOARD Z JSONU - DONE
DUPLICITA OTAZOK - DONE
Scoreboard screen - DONE
MENU BUTTON - Done
CASOMIERA - Done
UI
OTAZKY JSON / SQLITE
Pridanie otazky
Dotuknutie viewmodelov
ODOMYKANIE TEM / mozno
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
    val timeRemaining by viewModel.timeRemaining.collectAsState()
    val isTimerRunning by viewModel.isTimerRunning.collectAsState()
    var startTime = System.currentTimeMillis()
    var animationProgress by remember { mutableStateOf(1f) }
    val peachPink = Color(0xFFFFDAB9)
    LaunchedEffect(key1 = otazka) {
        viewModel.setTimerRunning(true)
        viewModel.resetTimeRemaining()
        startTime = System.currentTimeMillis()
        animationProgress = 1f
        var secondsElapsed = 0
        while (viewModel.timeRemaining.value > 0 && viewModel.isTimerRunning.value) {
            delay(1000L)
            secondsElapsed++
            val elapsedMillis = System.currentTimeMillis() - startTime
            animationProgress = 1f - (elapsedMillis.toFloat() / 15000f)
            animationProgress = animationProgress.coerceAtLeast(0f)
            viewModel.decrementTimeRemaining()
            if (viewModel.timeRemaining.value == 0 && viewModel.isTimerRunning.value) {
                viewModel.click()
                viewModel.fieldToShow()
                viewModel.setTimerRunning(false)
                break
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize().background(peachPink).verticalScroll(
        rememberScrollState()
    )) {
        Spacer(modifier = Modifier.height(16.dp))
        LinearProgressIndicator(
            progress = animationProgress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    viewModel.unclick()
                    viewModel.fieldToHide()
                    viewModel.resetScore()
                    navigator.navigate(MainMenu.Menu.name) },
                modifier = Modifier.wrapContentSize()
            ) {
                Text("Menu")
            }
        }
        //Spacer(modifier = Modifier.height(56.dp))



        //Spacer(modifier = Modifier.height(60.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = otazka.question,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = Int.MAX_VALUE,
                overflow = TextOverflow.Visible
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Score: $score",
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
            ,fontSize = 18.sp)
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
                vypisPole(viewModel = viewModel, navigator = navigator)
            }
            if (viewModel.timeRemaining.value == 0){
                ukazButtonSpatne(onDone = {
                    navigator.navigate(MainMenu.Menu.name)
                    viewModel.resetQuestion()
                    viewModel.unclick()
                    viewModel.resetScore()
                }, true)
            } else{
                ukazButtonSpatne(onDone = {
                    navigator.navigate(MainMenu.Menu.name)
                    viewModel.resetQuestion()
                    viewModel.unclick()
                    viewModel.resetScore()
                })
            }

        }

    }
}
@Composable
fun vypisPole(viewModel: ThemeSelectionViewModel, navigator: NavController){
    val meno by viewModel.meno.collectAsState()
    val score by viewModel.score.collectAsState()
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        OutlinedTextField(value = meno,
            onValueChange = {text -> viewModel.setMeno(text)},
            label = { Text("Meno") }, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(3.dp))
        Button(
            onClick = {
                viewModel.unclick()
                viewModel.fieldToHide()
                viewModel.saveScore(meno, score)
                viewModel.resetScore()
                //viewModel.resetQuestion()
                navigator.navigate(MainMenu.Menu.name)
            }
        ) {
            Text("OK")
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
    Spacer(modifier = Modifier.height(16.dp))
    Button(

        onClick = {
            if (clicked && !isCorrect || clicked && isCorrect){

            }
            else {
                viewModel.setTimerRunning(false)
                verifyAnswer(
                    answer = indexOdpovede,
                    otazka = otazka,
                    viewModel = viewModel,
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
    ) {
        Text(otazka.options.get(indexOdpovede))
    }
}

fun verifyAnswer(
    answer: Int,
    otazka: Otazky,
    viewModel: ThemeSelectionViewModel,
) {
    viewModel.setTimerRunning(false)
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
    Spacer(modifier = Modifier.height(16.dp))
    Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        Text(text = "Spravna odpoved!")
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

    Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = onDone,
            modifier = Modifier.padding(6.dp)
        ) {
            Text("Zapis score")
        }
    }
}
@Composable
fun ukazButtonSpatne(onDone: () -> Unit, cas: Boolean = false){
    Spacer(modifier = Modifier.height(6.dp))
    Row (modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center){
            if (cas){
                Text(
                    text = "Vyprsal ti cas!"
                )
            } else{
                Text(
                    text = "Nespravna odpoved"
                )
            }



    }
    /*
    Button(
        onClick = onDone,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Menu")
    }
     */
}