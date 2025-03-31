package com.abhay.alumniconnect.presentation.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.AlumniConnectTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column {
        Text("HomeScreen")
    }
}

@Preview(
    showBackground = true,
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun HomeScreenPreview() {
    AlumniConnectTheme {
        HomeScreen(

        )
    }
}