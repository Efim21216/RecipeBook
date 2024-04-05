package ru.nsu.reciepebook.ui.navigation.screens

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.nsu.reciepebook.ui.screen.filter.SearchFilter
import ru.nsu.reciepebook.ui.screen.filter.SearchFilterViewModel

fun NavGraphBuilder.searchFilter(
    navController: NavHostController,
    route: String,
    onDone: (Array<String>) -> Unit
) {
    composable(
        route = route
    ) {
        val viewModel = hiltViewModel<SearchFilterViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        SearchFilter(
            uiState = uiState.value,
            onEvent = viewModel::onEvent,
            uiEvent = viewModel.uiEvent,
            navigateUp = {
                navController.navigateUp()
            },
            onDone = onDone
        )
    }
}