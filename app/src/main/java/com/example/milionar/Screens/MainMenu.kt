package com.example.milionar.Screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.milionar.ViewModels.DifficultyViewModel
import com.example.milionar.ViewModels.GameViewModel
import com.example.milionar.ViewModels.ScoreboardViewModel
import com.example.milionar.ViewModels.ThemeSelectionViewModel

enum class MainMenu {
    Menu,
    Hra,
    Tema,
    ScoreBoard
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MilionarApp(
    themeViewModel: ThemeSelectionViewModel,
    viewModel: GameViewModel,
    difficultyViewModel: DifficultyViewModel,
    scoreViewModel: ScoreboardViewModel,
    navController: NavHostController = rememberNavController()
) {
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsState()
    val selectedTheme by viewModel.selectedTheme.collectAsState()
    //val selectedDifficulty = viewModel.selectedDifficulty
    NavHost(navController = navController, startDestination = MainMenu.Menu.name) {
        composable(MainMenu.Menu.name) {
            MainMenuScreen(
                selectedTheme = selectedTheme,
                selectedDifficulty = selectedDifficulty,
                onDifficultySelected = {
                    difficultyViewModel.setDifficulty(it)
                    //viewModel.setQuestions()
                },
                onSelectThemeClick = {
                    navController.navigate(MainMenu.Tema.name)
                },
                onStartGameClick = {
                    viewModel.setQuestions()
                    navController.navigate(MainMenu.Hra.name)
                },
                onDalejClick = {
                    navController.navigate(MainMenu.Hra.name)
                },
                onMenuClick = {
                    navController.navigate(MainMenu.Menu.name)
                },
                onScoreBoardClick = {
                    navController.navigate(MainMenu.ScoreBoard.name)
                }

            )
        }
        composable(MainMenu.Tema.name) {
            ThemeSelectionScreen(
                viewModel = themeViewModel,
                onDone = { navController.navigate(MainMenu.Menu.name) })
        }
        composable(MainMenu.Hra.name) {
            GameScreen(
                viewModel = viewModel,
                onDone = { navController.navigate(MainMenu.Menu.name) },
                navigator = navController
            )
        }
        composable(MainMenu.ScoreBoard.name) {
            ScoreboardScreen(scoreViewModel.scoreboard, navController, scoreViewModel)
        }
    }
}

@Composable
fun MainMenuScreen(
    selectedTheme: String,
    selectedDifficulty: String?,
    onDifficultySelected: (String) -> Unit,
    onSelectThemeClick: () -> Unit,
    onStartGameClick: () -> Unit,
    onDalejClick: () -> Unit,
    onMenuClick: () -> Unit,
    onScoreBoardClick: () -> Unit
) {
    val peachPink = Color(0xFFFFDAB9)
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(peachPink)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Ako sa stať milionárom?",
                    fontSize = 28.sp,
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                )

                Text("Kvíz", style = MaterialTheme.typography.h6)

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onStartGameClick, modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.hsl(120f, 0.5f, 0.5f))
                ) {
                    Text("Začni kvíz", style = TextStyle(fontWeight = FontWeight.Bold))
                }

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = onSelectThemeClick, modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.hsl(27.2f, 0.733f, 0.367f))
                ) {
                    Text("Zvoľ tému", style = TextStyle(fontWeight = FontWeight.Bold))
                }

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = onScoreBoardClick, modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color.hsl(27.2f, 0.733f, 0.367f))
                ) {
                    Text("Zobraz rebríček", style = TextStyle(fontWeight = FontWeight.Bold))
                }

                Spacer(modifier = Modifier.height(32.dp))


                Text("Zvoľ obtiažnosť", style = MaterialTheme.typography.h6)
                DifficultySelection(selectedDifficulty, onDifficultySelected)
                var them = selectedTheme
                if (them.equals("")){
                    them = "Random"
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text("Zvolená téma: $them", style = MaterialTheme.typography.h6)
            }
        }
    }
}

@Composable
fun DifficultySelection(
    selectedDifficulty: String?,
    onDifficultySelected: (String) -> Unit
) {
    Column {
        DifficultyOption(
            text = "Lahka",
            selected = selectedDifficulty == "Lahka",
            onSelected = onDifficultySelected)
        DifficultyOption(
            text = "Stredna",
            selected = selectedDifficulty == "Stredna",
            onSelected = onDifficultySelected)
        DifficultyOption(
            text = "Tazka",
            selected = selectedDifficulty == "Tazka",
            onSelected = onDifficultySelected)
    }
}

@Composable
fun DifficultyOption(text: String, selected: Boolean, onSelected: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = selected, onCheckedChange = { isChecked ->
            if (isChecked) {
                onSelected(text)
            } else {
                onSelected("")
            }
        }, colors = CheckboxDefaults.colors(Color.Black))

        Text(text)
    }
}