package ru.nsu.reciepebook.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.nsu.reciepebook.MainViewModel
import ru.nsu.reciepebook.service.CountdownService
import ru.nsu.reciepebook.ui.components.BottomNavigation
@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String,
    countdownService: CountdownService,
    viewModel: MainViewModel
) {
    LaunchedEffect(key1 = true) {
        navController.addOnDestinationChangedListener { controller, _, _ ->
            val routes = controller.currentBackStack.value
                .map { it.destination.route }
                .joinToString(", ")
            Log.d("MyTag", "BackStack: $routes")
        }
        viewModel.navEvent.collect {
            when (it) {
                is MainViewModel.UIEvent.Navigate -> {
                    navController.navigate(it.to)
                }
            }
        }
    }
    BottomNavigation(navController = navController) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            authGraph(navController)
            mainGraph(navController, countdownService)
        }
    }
}
