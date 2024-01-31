package ru.nsu.reciepebook.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.components.BottomNavigation
import ru.nsu.reciepebook.ui.screen.authorization.AuthorizationScreen
import ru.nsu.reciepebook.ui.screen.registration.RegistrationScreen

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.AuthorizationScreen.route,
        route = Screen.AuthGraph.route
    ) {
        composable(
            route = Screen.RegistrationScreen.route
        ) {
            BottomNavigation(
                navController = navController) {
                RegistrationScreen(navController)
            }
        }
        composable(
            route = Screen.AuthorizationScreen.route
        ) {
            BottomNavigation(
                navController = navController) {
                AuthorizationScreen(navController)
            }
        }
    }
}