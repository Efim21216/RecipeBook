package ru.nsu.reciepebook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.nsu.reciepebook.ui.components.BottomNavigation

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String
) {
    BottomNavigation(navController = navController) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            authGraph(navController)
            mainGraph(navController)
        }
    }
}