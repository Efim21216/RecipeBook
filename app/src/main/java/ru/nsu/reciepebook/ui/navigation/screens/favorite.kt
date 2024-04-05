package ru.nsu.reciepebook.ui.navigation.screens

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ru.nsu.reciepebook.service.CountdownService
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.favorite.Favorite
import ru.nsu.reciepebook.ui.screen.favorite.FavoriteViewModel

fun NavGraphBuilder.favorite(
    navController: NavHostController,
    countdownService: CountdownService
) {
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
}