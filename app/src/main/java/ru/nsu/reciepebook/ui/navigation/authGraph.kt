package ru.nsu.reciepebook.ui.navigation


import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.authorization.AuthorizationScreen
import ru.nsu.reciepebook.ui.screen.authorization.AuthorizationViewModel
import ru.nsu.reciepebook.ui.screen.registration.RegistrationScreen
import ru.nsu.reciepebook.ui.screen.registration.RegistrationViewModel

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.AuthorizationScreen.route,
        route = Graph.AuthGraph.route
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
}