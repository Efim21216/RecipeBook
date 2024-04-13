package ru.nsu.reciepebook.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ru.nsu.reciepebook.service.CountdownService
import ru.nsu.reciepebook.ui.Graph

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    countdownService: CountdownService
) {
    navigation(
        startDestination = Graph.HomeGraph.route,
        route = Graph.MainGraph.route
    ) {
        homeGraph(navController)
        profileGraph(navController, countdownService)
        searchGraph(navController)
        cookingGraph(navController, countdownService)
    }
}