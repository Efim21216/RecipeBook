package ru.nsu.reciepebook.ui.navigation.screens

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.nsu.reciepebook.service.CountdownService
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.cooking.CookingViewModel
import ru.nsu.reciepebook.ui.screen.cooking.InteractiveCooking

fun NavGraphBuilder.cookingScreen(
    navController: NavHostController,
    countdownService: CountdownService
) {
    composable(
        route = Screen.CookingScreen.route
    ) {
        val viewModel = hiltViewModel<CookingViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        viewModel.setTime(countdownService)
        InteractiveCooking(
            uiState = uiState.value,
            timerState = countdownService.timerState
                .collectAsStateWithLifecycle().value,
            onEvent = viewModel::onEvent,
            uiEvent = viewModel.uiEvent,
            navigateUp = {
                if (navController.previousBackStackEntry?.destination?.route == Screen.ProfileScreen.route)
                    navController.navigateUp()
                else
                    navController.navigate(Screen.ProfileScreen.route) {
                        popUpTo(Screen.CookingScreen.route) {
                            inclusive = true
                        }
                    }
            }
        )
    }
}