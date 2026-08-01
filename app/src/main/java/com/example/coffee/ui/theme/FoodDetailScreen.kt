package com.example.coffee.ui.theme

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp

@Composable
fun FoodDetailScreen(
    routeId: String,
    onBack: () -> Unit,
    onNext: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var quantityText by remember { mutableStateOf("1")}
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Food Details",
            style = MaterialTheme.typography.headlineSmall
        )
        Text (
            text = "Selected: $routeId",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 12.dp, bottom = 24.dp)
        )

        OutlinedTextField(
            value = quantityText,
            onValueChange = { quantityText = it.filter(Char::isDigit) },
            label = { Text("Quantity")}
        )

        Button(
            onClick = onBack,
            modifier = Modifier.padding(top = 8.dp)

        ) {
            Text("Next")
        }

        Button (
            onClick = onBack,
            modifier = Modifier.padding(top = 8.dp)

        ) {
            Text("Back")
        }

    }
}