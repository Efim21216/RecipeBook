package ru.nsu.reciepebook.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.home.HomeScreen
import ru.nsu.reciepebook.ui.screen.home.HomeViewModel

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.HomeScreen.route,
        route = Graph.HomeGraph.route
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
                toMyRecipes = { navController.navigate(Screen.MyRecipesScreen.route) }
            )
        }
    }
}