package com.example.milionar

import android.content.res.Resources.Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class MainMenu {
    Menu,
    Hra,
    Tema
}

@Composable
fun MilionarApp(
    viewModel: ThemeSelectionViewModel,
    navController: NavHostController = rememberNavController()
) {
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsState()
    //val selectedDifficulty = viewModel.selectedDifficulty
    NavHost(navController = navController, startDestination = MainMenu.Menu.name) {
        composable(MainMenu.Menu.name) {
            MainMenuScreen(
                selectedDifficulty = selectedDifficulty,
                onDifficultySelected = {
                    viewModel.setDifficulty(it)
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
                }
            )
        }
        composable(MainMenu.Tema.name) {
            ThemeSelectionScreen(
                viewModel = viewModel,
                onDone = { navController.navigate(MainMenu.Menu.name) })
        }
        composable(MainMenu.Hra.name) {
            GameScreen(
                viewModel = viewModel,
                onDone = { navController.navigate(MainMenu.Menu.name) },
                navigator = navController)
        }
    }
}

@Composable
fun MainMenuScreen(
    selectedDifficulty: String?,
    onDifficultySelected: (String) -> Unit,
    onSelectThemeClick: () -> Unit,
    onStartGameClick: () -> Unit,
    onDalejClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    Column {
        Text(text = "Menu")
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = {
            onSelectThemeClick()
        }) {
            Text(text = "Zvol temu")
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Zvol obtiaznost")
        Row {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedDifficulty == "Lahka",
                    onCheckedChange = { isChecked ->
                        if (isChecked) onDifficultySelected("Lahka")
                        else onDifficultySelected("")

                    }
                )
                Text("Lahka")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedDifficulty == "Stredna",
                    onCheckedChange = { isChecked ->
                        if (isChecked) onDifficultySelected("Stredna")
                        else onDifficultySelected("")
                    }
                )
                Text("Stredna")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedDifficulty == "Tazka",
                    onCheckedChange = { isChecked ->
                        if (isChecked) onDifficultySelected("Tazka")
                        else onDifficultySelected("")
                    }
                )
                Text("Tazka")
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        TextButton(onClick = {
            onStartGameClick()
        }) {
            Text(text = "Zacni kviz")
        }
    }

}