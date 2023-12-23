package ru.nsu.reciepebook.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.nsu.reciepebook.ui.screen.authorization.AuthorizationScreen
import ru.nsu.reciepebook.ui.screen.registration.RegistrationScreen
import ru.nsu.reciepebook.ui.screen.secret.SecretScreen

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = Screen.RegistrationScreen.route
        ) {
            RegistrationScreen(onNavigate = {
                navController.navigate(it.route)
            })
        }
        composable(
            route = Screen.AuthorizationScreen.route
        ) {
            AuthorizationScreen(onNavigate = {
                navController.navigate(it.route)
            })
        }
        composable(
            route = Screen.SecretScreen.route
        ) {
            SecretScreen()
        }
    }
}