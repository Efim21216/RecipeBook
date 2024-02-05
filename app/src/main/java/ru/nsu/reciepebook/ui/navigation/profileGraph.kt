package ru.nsu.reciepebook.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.addRecipeInfo.AddRecipeInfo
import ru.nsu.reciepebook.ui.screen.myRecipes.MyRecipes
import ru.nsu.reciepebook.ui.screen.profile.ProfileScreen
import ru.nsu.reciepebook.ui.screen.recipeInfo.RecipeInfo

fun NavGraphBuilder.profileGraph(navController: NavHostController) {
    navigation(
        startDestination = Screen.ProfileScreen.route,
        route = Graph.ProfileGraph.route
    ) {
        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(navController)
        }
        composable(
            route = Screen.MyRecipesScreen.route
        ) {
            MyRecipes(navController)
        }
        composable(
            route = Screen.AddRecipeInfoScreen.route
        ) {
            AddRecipeInfo(navController)
        }
        composable(
            route = Screen.RecipeInfoScreen.route + "/{recipeId}"
        ) {
            RecipeInfo(navController, it.arguments?.getString("recipeId")!!)
        }
    }
}