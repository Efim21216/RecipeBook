package ru.nsu.reciepebook.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.nsu.reciepebook.ui.components.BottomNavigation
@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String
) {
    LaunchedEffect(key1 = true) {
        navController.addOnDestinationChangedListener { controller, _, _ ->
            val routes = controller.currentBackStack.value
                .map { it.destination.route }
                .joinToString(", ")
            Log.d("MyTag", "BackStack: $routes")
        }
    }
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
