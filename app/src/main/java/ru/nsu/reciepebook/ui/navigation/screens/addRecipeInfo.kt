package ru.nsu.reciepebook.ui.navigation.screens

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.sharedViewModel
import ru.nsu.reciepebook.ui.screen.add_recipe.AddRecipeViewModel
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.AddRecipeInfo

fun NavGraphBuilder.addRecipeInfo(
    navController: NavHostController
) {
    composable(
        route = Screen.AddRecipeInfoScreen.route
    ) {
        val viewModel = it.sharedViewModel<AddRecipeViewModel>(navController = navController)
        val uiState = viewModel.uiStateInfo.collectAsStateWithLifecycle()
        AddRecipeInfo(
            uiState = uiState.value,
            onEvent = viewModel::onEventInfo,
            uiEvent = viewModel.uiEventInfo,
            navigateUp = { navController.navigateUp() },
            toAddIngredients = { navController.navigate(Screen.AddRecipeIngredientsScreen.route) }
        )
    }
}