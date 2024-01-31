package ru.nsu.reciepebook.ui.navigation

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.components.NavigationWrapper
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
            NavigationWrapper(
                navController = navController,
                title = stringResource(id = R.string.registration),
                isShowBottomBar = false) {
                RegistrationScreen(navController)
            }
        }
        composable(
            route = Screen.AuthorizationScreen.route
        ) {
            NavigationWrapper(
                navController = navController,
                title = stringResource(id = R.string.authorization),
                isShowBottomBar = false) {
                AuthorizationScreen(navController)
            }
        }
    }
}