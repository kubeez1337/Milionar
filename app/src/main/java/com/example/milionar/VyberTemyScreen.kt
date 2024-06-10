package com.example.milionar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ThemeSelectionScreen(
    viewModel: ThemeSelectionViewModel,
    onDone: () -> Unit // Callback to navigate back
) {
    val selectedTheme by viewModel.selectedTheme.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Your theme selection UI here (e.g., RadioButtons, Dropdowns)
        // Example with RadioButtons:
        val themes = listOf("Matematika", "Science", "History", "Geography")
        themes.forEach { theme ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedTheme == theme,
                    onClick = { viewModel.setTheme(theme) }
                )
                Text(theme)
            }
        }
        Spacer(modifier = Modifier.weight(1f)) // Push the button to the bottom
        Button(
            onClick = onDone,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Done")
        }
    }
}