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
import ru.nsu.reciepebook.ui.navigation.screens.recipeInfo
import ru.nsu.reciepebook.ui.navigation.screens.searchFilter
import ru.nsu.reciepebook.ui.navigation.screens.searchScreen
import ru.nsu.reciepebook.ui.screen.search.SearchScreen
import ru.nsu.reciepebook.ui.screen.search.SearchViewModel

fun NavGraphBuilder.searchGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.SearchScreen.route,
        route = Graph.SearchGraph.route
    ) {
        searchScreen(navController)
        searchFilter(navController, Screen.FilterScreen.route, {})
        recipeInfo(navController)
    }
}