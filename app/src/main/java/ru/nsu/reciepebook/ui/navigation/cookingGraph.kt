package ru.nsu.reciepebook.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ru.nsu.reciepebook.service.CountdownService
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.screens.cookingInfoScreen
import ru.nsu.reciepebook.ui.navigation.screens.cookingScreen

fun NavGraphBuilder.cookingGraph(
    navController: NavHostController,
    countdownService: CountdownService
) {
    navigation(
        startDestination = Screen.CookingScreen.route,
        route = Graph.CookingGraph.route
    ){
        cookingScreen(navController, countdownService)
        cookingInfoScreen(navController)
    }
}