package ru.nsu.reciepebook.ui.navigation


import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.screens.authorizationScreen
import ru.nsu.reciepebook.ui.navigation.screens.registrationScreen
import ru.nsu.reciepebook.ui.screen.authorization.AuthorizationScreen
import ru.nsu.reciepebook.ui.screen.authorization.AuthorizationViewModel
import ru.nsu.reciepebook.ui.screen.registration.RegistrationScreen
import ru.nsu.reciepebook.ui.screen.registration.RegistrationViewModel

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.AuthorizationScreen.route,
        route = Graph.AuthGraph.route
    ) {
        registrationScreen(navController)
        authorizationScreen(navController)
    }
}