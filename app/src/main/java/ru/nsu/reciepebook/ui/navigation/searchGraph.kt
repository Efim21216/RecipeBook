package ru.nsu.reciepebook.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.screens.recipeInfo
import ru.nsu.reciepebook.ui.navigation.screens.searchFilter
import ru.nsu.reciepebook.ui.navigation.screens.searchScreen
fun NavGraphBuilder.searchGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.SearchScreen.route,
        route = Graph.SearchGraph.route
    ) {
        searchScreen(navController)
        searchFilter(navController, Screen.FilterScreen.route, {})
        recipeInfo(navController)
    }
}