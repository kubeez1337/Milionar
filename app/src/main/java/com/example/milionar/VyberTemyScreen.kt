package com.example.milionar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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

@Composable
fun ThemeSelectionScreen(
    viewModel: ThemeSelectionViewModel,
    onDone: () -> Unit
) {
    val selectedTheme by viewModel.selectedTheme.collectAsState()
    val peachPink = Color(0xFFFFDAB9)
    val themes = listOf("Matematika", "Science", "History", "Geography", "Random")

    Column(modifier = Modifier
        .fillMaxSize()
        .background(peachPink)
        .padding(16.dp), verticalArrangement = Arrangement.Top) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "            Theme selection",
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        themes.forEach { theme ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = selectedTheme == theme,
                    onClick = {
                        viewModel.setTheme(theme)
                        if (theme == "Random") {
                            viewModel.setTheme("")
                        }
                    }, colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Black
                    )
                )
                Text(theme,style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.height(260.dp))
        Button(
            onClick = onDone,
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.hsl(27.2f, 0.733f, 0.367f))
        ) {
            Text("Done", style = TextStyle(fontWeight = FontWeight.Bold))
        }
    }
}