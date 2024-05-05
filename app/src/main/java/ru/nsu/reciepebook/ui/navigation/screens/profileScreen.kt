package ru.nsu.reciepebook.ui.navigation.screens

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.profile.ProfileScreen
import ru.nsu.reciepebook.ui.screen.profile.ProfileViewModel

fun NavGraphBuilder.profileScreen(
    navController: NavHostController
) {
    composable(
        route = Screen.ProfileScreen.route
    ) {
        val viewModel = hiltViewModel<ProfileViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        ProfileScreen(
            uiState = uiState.value,
            onEvent = viewModel::onEvent,
            uiEvent = viewModel.uiEvent,
            toMyRecipes = {
                navController.navigate(Screen.MyRecipesScreen.route)
            },
            toAddRecipeInfo = {
                navController.navigate(Graph.AddRecipeGraph.route)
            },
            toFavorite = {
                navController.navigate(Screen.FavoriteScreen.route)
            },
            toSubscriptions = {
                navController.navigate(Graph.CookingGraph.route)
            }
        )
    }
}