package com.example.bryjchallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bryjchallenge.ui.mainscreen.HomeScreen
import com.example.bryjchallenge.ui.mainscreen.MainScreenViewModel

@Composable
fun ApplicationNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ApplicationDestinations.HOME_ROUTE,
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable (route = ApplicationDestinations.HOME_ROUTE) { _ ->
            val homeViewModel: MainScreenViewModel = hiltViewModel()
            HomeScreen(uiState = homeViewModel::uiState.get(),
                itemClickFunction = homeViewModel::itemClick
            )
        }
    }
}