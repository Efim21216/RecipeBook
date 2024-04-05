package ru.nsu.reciepebook.ui.navigation.screens

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.search.SearchScreen
import ru.nsu.reciepebook.ui.screen.search.SearchViewModel

fun NavGraphBuilder.searchScreen(
    navController: NavHostController
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
}