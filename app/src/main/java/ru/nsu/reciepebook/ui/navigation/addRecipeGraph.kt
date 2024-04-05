package ru.nsu.reciepebook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.screens.addRecipeInfo
import ru.nsu.reciepebook.ui.navigation.screens.addRecipeIngredients
import ru.nsu.reciepebook.ui.navigation.screens.addRecipeStep
import ru.nsu.reciepebook.ui.screen.add_recipe.AddRecipeViewModel
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.AddRecipeInfo
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.AddRecipeIngredients
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep.AddRecipeStep
import ru.nsu.reciepebook.util.Constants.Companion.RECIPE_ID_ARG


fun NavGraphBuilder.addRecipeGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.AddRecipeInfoScreen.route,
        route = Graph.AddRecipeGraph.route
    ) {
        addRecipeInfo(navController)
        addRecipeStep(navController)
        addRecipeIngredients(navController)
    }
}
@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel<T>(parentEntry)
}