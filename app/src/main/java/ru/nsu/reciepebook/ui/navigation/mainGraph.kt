package ru.nsu.reciepebook.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.home.HomeScreen
import ru.nsu.reciepebook.ui.screen.profile.ProfileScreen
import ru.nsu.reciepebook.ui.screen.search.SearchScreen

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.HomeScreen.route,
        route = Screen.MainGraph.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen()
        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen()
        }
        composable(
            route = Screen.SearchScreen.route
        ) {
            SearchScreen()
        }
    }
}