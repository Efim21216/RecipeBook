package ru.nsu.reciepebook.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.recipeInfo.composableRecipeInfo
import ru.nsu.reciepebook.ui.screen.search.SearchScreen
import ru.nsu.reciepebook.ui.screen.search.SearchViewModel

fun NavGraphBuilder.searchGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.SearchScreen.route,
        route = Graph.SearchGraph.route
    ) {
        composable(
            route = Screen.SearchScreen.route
        ) {
            val viewModel = hiltViewModel<SearchViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            SearchScreen(
                uiState = uiState.value,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent
            )
        }
        composableRecipeInfo(navController)
    }
}