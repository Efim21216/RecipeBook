package ru.nsu.reciepebook.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.screens.homeScreen
import ru.nsu.reciepebook.ui.screen.home.HomeScreen
import ru.nsu.reciepebook.ui.screen.home.HomeViewModel

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.HomeScreen.route,
        route = Graph.HomeGraph.route
    ) {
        homeScreen(navController)
    }
}