package ru.nsu.reciepebook.ui.navigation.screens

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.search.SearchScreen
import ru.nsu.reciepebook.ui.screen.search.SearchViewModel
import ru.nsu.reciepebook.util.Constants

fun NavGraphBuilder.searchScreen(
    navController: NavHostController
) {
    composable(
        route = Screen.SearchScreen.route + "?${Constants.TAGS_ARG}={${Constants.TAGS_ARG}}",
        arguments = listOf(
            navArgument(name = Constants.TAGS_ARG) {
                type = StringArrayType
                defaultValue = emptyArray<String>()
            }
        )
    ) {
        val viewModel = hiltViewModel<SearchViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        if (it.arguments?.getStringArray(Constants.TAGS_ARG) != null)
            viewModel.setTags(it.arguments?.getStringArray(Constants.TAGS_ARG)!!)
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