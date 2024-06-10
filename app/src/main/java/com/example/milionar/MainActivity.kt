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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.milionar.ui.theme.MilionarTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val scoreManager = ScoreManager(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MilionarTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MilionarApp(viewModel = ThemeSelectionViewModel(scoreManager))
                    /*
                    DifficultySelection(
                        selectedDifficulty = selectedDifficulty.toString(),
                        onDifficultySelected = { newDifficulty ->
                            selectedDifficulty = newDifficulty
                            println("Selected Difficulty: $newDifficulty")
                        }
                    )
                    */
                }
            }
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun GreetingPreview() {
    MilionarTheme {
        Greeting("S")
        }
}
/*
@Preview
@Composable
fun HomeScreenPreview() {
    var selectedDifficulty by remember { mutableStateOf<String?>(null) }

    Column {
        selectedDifficulty?.let {
            DifficultySelection(
                selectedDifficulty = it,
                onDifficultySelected = { newDifficulty ->
                    selectedDifficulty = newDifficulty
                    println("Selected Difficulty: $newDifficulty")
                }
            )
        }
    }
}
*/
@Preview(showBackground = true)
@Composable
fun CompoButton(onClick: () -> Unit = {}) {
    Button(onClick = onClick) {
        Text("Start")
    }
}
@Composable
fun DifficultySelection(selectedDifficulty: String, onDifficultySelected: (String) -> Unit){
    Column {
        Text(text = "Zvol obtiaznost")
        Row {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedDifficulty == "Lahka",
                    onCheckedChange = { isChecked ->
                        if (isChecked) onDifficultySelected("Lahka")
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
                    }
                )
                Text("Tazka")
            }
        }
    }
}
