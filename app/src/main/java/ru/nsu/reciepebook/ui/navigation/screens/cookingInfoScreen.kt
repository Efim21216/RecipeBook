package ru.nsu.reciepebook.ui.navigation.screens

import CookingInfo
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.sharedViewModel
import ru.nsu.reciepebook.ui.screen.cooking.CookingViewModel
import ru.nsu.reciepebook.util.Constants

fun NavGraphBuilder.cookingInfoScreen(
    navController: NavHostController
) {
    composable(
        route = Screen.CookingInfoScreen.route + "?${Constants.RECIPE_ID_ARG}={${Constants.RECIPE_ID_ARG}}",
        arguments = listOf(
            navArgument(name = Constants.RECIPE_ID_ARG) {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) {
        val viewModel = it.sharedViewModel<CookingViewModel>(navController = navController)
        val uiState = viewModel.uiStateInfo.collectAsStateWithLifecycle()
        CookingInfo(
            uiState = uiState.value,
            navigateUp = { onBack(navController) },
            toStep = {idx ->
                navController.navigate(Screen.CookingScreen.route + "?${Constants.RECIPE_ID_ARG}=${it
                    .arguments?.getInt(Constants.RECIPE_ID_ARG)}" + "&${Constants.STEP_NUMBER}=$idx")
            }
            )
    }
}