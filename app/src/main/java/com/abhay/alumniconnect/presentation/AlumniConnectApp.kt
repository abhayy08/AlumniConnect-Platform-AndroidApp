package com.abhay.alumniconnect.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.abhay.alumniconnect.presentation.navigation.graphs.RootNavGraph
import com.example.compose.AlumniConnectTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AlumniConnectApp() {
    AlumniConnectTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            val navController = rememberNavController()
            RootNavGraph(navController)
        }
    }
}


@Preview
@Composable
private fun AlumniConnectAppPreview() {
    AlumniConnectTheme {
        AlumniConnectApp()
    }
}