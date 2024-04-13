package ru.nsu.reciepebook.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ru.nsu.reciepebook.service.CountdownService
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.screens.cookingScreen
import ru.nsu.reciepebook.ui.screen.cooking.CookingViewModel

fun NavGraphBuilder.cookingGraph(
    navController: NavHostController,
    countdownService: CountdownService
) {
    navigation(
        startDestination = Screen.CookingScreen.route,
        route = Graph.CookingGraph.route
    ){
        cookingScreen(navController, countdownService)
    }
}