package ru.nsu.reciepebook.ui.navigation

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.components.NavigationWrapper
import ru.nsu.reciepebook.ui.screen.home.HomeScreen
import ru.nsu.reciepebook.ui.screen.profile.ProfileScreen
import ru.nsu.reciepebook.ui.screen.search.SearchScreen

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.HomeScreen.route,
        route = Screen.MainGraph.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            NavigationWrapper(
                navController = navController,
                title = stringResource(id = R.string.main),
                isShowBottomBar = true) {
                HomeScreen()
            }

        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            NavigationWrapper(
                navController = navController,
                title = stringResource(id = R.string.profile),
                isShowBottomBar = true) {
                ProfileScreen()
            }

        }
        composable(
            route = Screen.SearchScreen.route
        ) {
            NavigationWrapper(
                navController = navController,
                title = stringResource(id = R.string.search),
                isShowBottomBar = true) {
                SearchScreen()
            }
        }
    }
}