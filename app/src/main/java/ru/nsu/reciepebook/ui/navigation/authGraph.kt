package ru.nsu.reciepebook.ui.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.authorization.AuthorizationScreen
import ru.nsu.reciepebook.ui.screen.registration.RegistrationScreen

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.AuthorizationScreen.route,
        route = Graph.AuthGraph.route
    ) {
        composable(
            route = Screen.RegistrationScreen.route
        ) {
            RegistrationScreen(navController)
        }
        composable(
            route = Screen.AuthorizationScreen.route
        ) {
            AuthorizationScreen(
                toMain = {
                    navController.navigate(Graph.MainGraph.route) {
                        popUpTo(Screen.AuthorizationScreen.route) {
                            inclusive = true
                        }
                    }
                },
                toRegister = {
                    navController.navigate(Screen.RegistrationScreen.route) {
                        popUpTo(Screen.AuthorizationScreen.route) {
                            inclusive = true
                        }
                    }
                })
        }
    }
}