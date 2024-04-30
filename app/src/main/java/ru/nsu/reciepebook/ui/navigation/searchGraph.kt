package ru.nsu.reciepebook.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.google.gson.Gson
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.screens.recipeInfo
import ru.nsu.reciepebook.ui.navigation.screens.searchFilter
import ru.nsu.reciepebook.ui.navigation.screens.searchScreen
import ru.nsu.reciepebook.util.Constants

fun NavGraphBuilder.searchGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.SearchScreen.route +"?${Constants.TAGS_ARG}={${Constants.TAGS_ARG}}",
        route = Graph.SearchGraph.route
    ) {
        searchScreen(navController)
        searchFilter(navController, Screen.FilterScreen.route) {
            navController.navigate(Screen.SearchScreen.route + "?${Constants.TAGS_ARG}=${Gson().toJson(it)}") {
                popUpTo(Screen.SearchScreen.route)
                launchSingleTop = true
                restoreState = true
            }
        }
        recipeInfo(navController)
    }
}