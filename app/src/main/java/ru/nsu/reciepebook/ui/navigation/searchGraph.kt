package ru.nsu.reciepebook.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.filter.SearchFilter
import ru.nsu.reciepebook.ui.screen.filter.SearchFilterViewModel
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
                uiEvent = viewModel.uiEvent,
                toFilter = {
                    navController.navigate(Screen.FilterScreen.route)
                }
            )
        }
        composable(
            route = Screen.FilterScreen.route
        ) {
            val viewModel = hiltViewModel<SearchFilterViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            SearchFilter(
                uiState = uiState.value,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
        composableRecipeInfo(navController)
    }
}