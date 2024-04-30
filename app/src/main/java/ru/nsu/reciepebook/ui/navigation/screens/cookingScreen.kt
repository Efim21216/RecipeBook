package ru.nsu.reciepebook.ui.navigation.screens

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import ru.nsu.reciepebook.service.CountdownService
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.sharedViewModel
import ru.nsu.reciepebook.ui.screen.cooking.CookingEvent
import ru.nsu.reciepebook.ui.screen.cooking.CookingViewModel
import ru.nsu.reciepebook.ui.screen.cooking.InteractiveCooking
import ru.nsu.reciepebook.util.Constants

fun NavGraphBuilder.cookingScreen(
    navController: NavHostController,
    countdownService: CountdownService
) {
    composable(
        route = Screen.CookingScreen.route + "?${Constants.RECIPE_ID_ARG}={${Constants.RECIPE_ID_ARG}}"
        + "&${Constants.STEP_NUMBER}={${Constants.STEP_NUMBER}}",
        arguments = listOf(
            navArgument(name = Constants.RECIPE_ID_ARG) {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument(name = Constants.STEP_NUMBER) {
                type = NavType.IntType
                defaultValue = 0
            }
        )
    ) {
        val viewModel = it.sharedViewModel<CookingViewModel>(navController = navController)
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        val stepNumber = it
            .arguments?.getInt(Constants.STEP_NUMBER) ?: 0
        viewModel.initStep(countdownService, stepNumber)
        InteractiveCooking(
            uiState = uiState.value,
            timerState = countdownService.timerState
                .collectAsStateWithLifecycle().value,
            recipeId = it
                .arguments?.getInt(Constants.RECIPE_ID_ARG)!!,
            onEvent = viewModel::onEvent,
            uiEvent = viewModel.uiEvent,
            navigateUp = { onBack(navController) },
            toStep = {idx ->
                viewModel.setStep(countdownService, idx)
            },
            toInfo = {
                navController.navigate(Screen.CookingInfoScreen.route) {
                    popUpTo(Graph.CookingGraph.route)
                }
            }
        )
    }
}
fun onBack(
    navController: NavHostController
) {
    //TODO
    if (navController.previousBackStackEntry?.destination?.route == Screen.ProfileScreen.route)
        navController.navigateUp()
    else
        navController.navigate(Screen.ProfileScreen.route) {
            popUpTo(Screen.CookingScreen.route) {
                inclusive = true
            }
        }
}