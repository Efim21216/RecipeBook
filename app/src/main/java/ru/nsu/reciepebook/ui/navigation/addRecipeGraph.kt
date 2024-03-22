package ru.nsu.reciepebook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.add_recipe.AddRecipeViewModel
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.AddRecipeInfo
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.AddRecipeIngredients
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep.AddRecipeStep
import ru.nsu.reciepebook.util.Constants.Companion.RECIPE_ID


fun NavGraphBuilder.addRecipeGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.AddRecipeInfoScreen.route,
        route = Graph.AddRecipeGraph.route
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
                toRecipeInfo = {
                    navController.navigate(Screen.RecipeInfoScreen.route + "?$RECIPE_ID=${viewModel.shareId}") {
                        popUpTo(Graph.AddRecipeGraph.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
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
}
@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel<T>(parentEntry)
}