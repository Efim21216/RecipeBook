package ru.nsu.reciepebook.ui.navigation.screens

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.authorization.AuthorizationScreen
import ru.nsu.reciepebook.ui.screen.authorization.AuthorizationViewModel

fun NavGraphBuilder.authorizationScreen(
    navController: NavHostController
) {
    composable(
        route = Screen.AuthorizationScreen.route
    ) {
        val viewModel = hiltViewModel<AuthorizationViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        AuthorizationScreen(
            uiState = uiState.value,
            onEvent = viewModel::onEvent,
            uiEvent = viewModel.uiEvent,
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