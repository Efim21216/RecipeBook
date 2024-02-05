package ru.nsu.reciepebook.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.recipeInfo.RecipeInfo
import ru.nsu.reciepebook.ui.screen.search.SearchScreen

fun NavGraphBuilder.searchGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.SearchScreen.route,
        route = Graph.SearchGraph.route
    ) {
        composable(
            route = Screen.SearchScreen.route
        ) {
            SearchScreen()
        }

        composable(
            route = Screen.RecipeInfoScreen.route + "/{recipeId}"
        ) {
            RecipeInfo(navController, it.arguments?.getString("recipeId")!!)
        }
    }
}