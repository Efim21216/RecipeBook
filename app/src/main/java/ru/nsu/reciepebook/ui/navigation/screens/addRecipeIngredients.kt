package ru.nsu.reciepebook.ui.navigation.screens

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.sharedViewModel
import ru.nsu.reciepebook.ui.screen.add_recipe.AddRecipeViewModel
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.AddRecipeIngredients

fun NavGraphBuilder.addRecipeIngredients(
    navController: NavHostController
) {
    composable(
        route = Screen.AddRecipeIngredientsScreen.route
    ) {
        val viewModel = it.sharedViewModel<AddRecipeViewModel>(navController = navController)
        val uiState = viewModel.uiStateIngredients.collectAsStateWithLifecycle()
        AddRecipeIngredients(
            uiState = uiState.value,
            onEvent = viewModel::onEventIngredients,
            uiEvent = viewModel.uiEventIngredients,
            navigateUp = { navController.navigateUp() },
            toAddStep = { navController.navigate(Screen.AddRecipeStepScreen.route) },
            toAddInfo = { navController.navigate(Screen.AddRecipeInfoScreen.route) {
                popUpTo(Screen.AddRecipeInfoScreen.route) {
                    inclusive = true
                }
            } }
        )
    }
}