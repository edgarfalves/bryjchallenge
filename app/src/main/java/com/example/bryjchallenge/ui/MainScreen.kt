package com.example.bryjchallenge.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.bryjchallenge.ui.navigation.ApplicationNavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    ApplicationNavGraph(
        navController = navController
    )
}