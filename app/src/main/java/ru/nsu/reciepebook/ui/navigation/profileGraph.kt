package ru.nsu.reciepebook.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.addRecipeInfo.AddRecipeInfo
import ru.nsu.reciepebook.ui.screen.addRecipeInfo.AddRecipeInfoViewModel
import ru.nsu.reciepebook.ui.screen.myRecipes.MyRecipes
import ru.nsu.reciepebook.ui.screen.myRecipes.MyRecipesViewModel
import ru.nsu.reciepebook.ui.screen.profile.ProfileScreen
import ru.nsu.reciepebook.ui.screen.profile.ProfileViewModel
import ru.nsu.reciepebook.ui.screen.recipeInfo.composableRecipeInfo
import ru.nsu.reciepebook.util.Constants


fun NavGraphBuilder.profileGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.ProfileScreen.route,
        route = Graph.ProfileGraph.route
    ) {
        composable(
            route = Screen.ProfileScreen.route
        ) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            ProfileScreen(
                uiState = uiState.value,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                toMyRecipes = {
                    navController.navigate(Screen.MyRecipesScreen.route)
                },
                toAddRecipeInfo = {
                    navController.navigate(Screen.AddRecipeInfoScreen.route)
                }
            )
        }
        composable(
            route = Screen.MyRecipesScreen.route
        ) {
            val viewModel = hiltViewModel<MyRecipesViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            MyRecipes(
                uiState = uiState.value,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                toRecipe = {
                    navController.navigate(Screen.RecipeInfoScreen.route + "?${Constants.RECIPE_ID}=$it")
                },
                navigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = Screen.AddRecipeInfoScreen.route
        ) {
            val viewModel = hiltViewModel<AddRecipeInfoViewModel>()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            AddRecipeInfo(
                uiState = uiState.value,
                onEvent = viewModel::onEvent,
                uiEvent = viewModel.uiEvent,
                navigateUp = { navController.navigateUp() }
            )
        }
        composableRecipeInfo(navController)
    }
}
