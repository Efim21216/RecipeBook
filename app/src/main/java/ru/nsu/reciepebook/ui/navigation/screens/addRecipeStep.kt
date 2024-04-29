package ru.nsu.reciepebook.ui.navigation.screens

import android.util.Log
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.sharedViewModel
import ru.nsu.reciepebook.ui.screen.add_recipe.AddRecipeViewModel
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep.AddRecipeStep
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep.AddRecipeStepEvent
import ru.nsu.reciepebook.util.Constants

fun NavGraphBuilder.addRecipeStep(
    navController: NavHostController
) {
    composable(
        route = Screen.AddRecipeStepScreen.route
    ) {
        val viewModel = it.sharedViewModel<AddRecipeViewModel>(navController = navController)
        val uiState = viewModel.uiStateStep.collectAsStateWithLifecycle()
        AddRecipeStep(
            uiState = uiState.value,
            onEvent = viewModel::onEventStep,
            uiEvent = viewModel.uiEventStep,
            navigateUp = { navController.navigateUp() },
            toAddInfo = { navController.navigate(Screen.AddRecipeInfoScreen.route) {
                popUpTo(Screen.AddRecipeInfoScreen.route) {
                    inclusive = true
                }
            } },
            toStep = {stepNumber ->
                viewModel.onEventStep(AddRecipeStepEvent.ToStep(stepNumber))
            },
            toAddIngredients = { navController.navigate(Screen.AddRecipeIngredientsScreen.route) },
            toRecipe = {recipeId ->
                navController.navigate(Screen.RecipeInfoScreen.route + "?${Constants.RECIPE_ID_ARG}=$recipeId") {
                    popUpTo(Graph.AddRecipeGraph.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}