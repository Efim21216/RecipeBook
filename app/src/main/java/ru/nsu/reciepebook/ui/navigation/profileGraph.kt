package ru.nsu.reciepebook.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.service.CountdownService
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.screens.myRecipesSearch
import ru.nsu.reciepebook.ui.screen.favorite.Favorite
import ru.nsu.reciepebook.ui.screen.favorite.FavoriteViewModel
import ru.nsu.reciepebook.ui.screen.profile.ProfileScreen
import ru.nsu.reciepebook.ui.screen.profile.ProfileViewModel
import ru.nsu.reciepebook.ui.screen.recipeInfo.composableRecipeInfo


fun NavGraphBuilder.profileGraph(
    navController: NavHostController,
    countdownService: CountdownService
) {
    navigation(
        startDestination = Screen.ProfileScreen.route,
        route = Graph.ProfileGraph.route
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
                }
            )
        }
        composable(
            route = Screen.FavoriteScreen.route
        ) {
            val viewModel = hiltViewModel<FavoriteViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            Favorite(
                uiState = uiState.value,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                countdownService = countdownService,
                navigateUp = { navController.navigateUp() }
            )
        }

        myRecipesSearch(navController)
        addRecipeGraph(navController)
        composableRecipeInfo(navController)
    }
}
