package ru.nsu.reciepebook.ui.navigation.screens

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.home.HomeScreen
import ru.nsu.reciepebook.ui.screen.home.HomeViewModel
import ru.nsu.reciepebook.util.Constants
import ru.nsu.reciepebook.util.Constants.Companion.RECIPE_ID_ARG

fun NavGraphBuilder.homeScreen(
    navController: NavHostController
) {
    composable(
        route = Screen.HomeScreen.route
    ) {
        val viewModel = hiltViewModel<HomeViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        HomeScreen(
            uiState = uiState.value,
            onEvent = viewModel::onEvent,
            uiEvent = viewModel.uiEvent,
            toFavorite = { navController.navigate(Screen.FavoriteScreen.route) },
            toAddRecipe = { navController.navigate(Screen.AddRecipeInfoScreen.route) },
            toMyRecipes = { navController.navigate(Screen.MyRecipesScreen.route) },
            toRecipeInfo = {
                navController.navigate(Screen.RecipeInfoScreen.route + "?$RECIPE_ID_ARG=$it")
            }
        )
    }
    recipeInfo(navController)
}