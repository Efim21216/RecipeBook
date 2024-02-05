package ru.nsu.reciepebook.ui.screen.recipeInfo

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.util.Constants

fun NavGraphBuilder.composableRecipeInfo(navController: NavHostController) {
    composable(
        route = Screen.RecipeInfoScreen.route + "?${Constants.RECIPE_ID}={recipeId}",
        arguments = listOf(
            navArgument(name = Constants.RECIPE_ID) {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) {
        val viewModel = hiltViewModel<RecipeInfoViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        RecipeInfo(
            uiState = uiState.value,
            onEvent = viewModel::onEvent,
            uiEvent = viewModel.uiEvent,
            recipeId = viewModel.recipeId.collectAsStateWithLifecycle().value,
            navigateUp = {navController.navigateUp()}
        )
    }
}