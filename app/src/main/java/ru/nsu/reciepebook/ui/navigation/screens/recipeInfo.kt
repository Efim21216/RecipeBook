package ru.nsu.reciepebook.ui.navigation.screens

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.recipeInfo.RecipeInfo
import ru.nsu.reciepebook.ui.screen.recipeInfo.RecipeInfoViewModel
import ru.nsu.reciepebook.util.Constants.Companion.RECIPE_ID_ARG

fun NavGraphBuilder.recipeInfo(navController: NavHostController) {
    composable(
        route = Screen.RecipeInfoScreen.route + "?$RECIPE_ID_ARG={$RECIPE_ID_ARG}",
        arguments = listOf(
            navArgument(name = RECIPE_ID_ARG) {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) {
        //Аргумент получаю внутри view model
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