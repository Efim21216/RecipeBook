package ru.nsu.reciepebook.ui.navigation.screens

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.registration.RegistrationScreen
import ru.nsu.reciepebook.ui.screen.registration.RegistrationViewModel

fun NavGraphBuilder.registrationScreen(
    navController: NavHostController
) {
    composable(
        route = Screen.RegistrationScreen.route
    ) {
        val viewModel = hiltViewModel<RegistrationViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        RegistrationScreen(
            uiState = uiState.value,
            onEvent = viewModel::onEvent,
            uiEvent = viewModel.uiEvent,
            toMain = {
                navController.navigate(Graph.MainGraph.route) {
                    popUpTo(Screen.RegistrationScreen.route) {
                        inclusive = true
                    }
                }
            },
            toAuth = {
                navController.navigate(Screen.AuthorizationScreen.route) {
                    popUpTo(Screen.RegistrationScreen.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}