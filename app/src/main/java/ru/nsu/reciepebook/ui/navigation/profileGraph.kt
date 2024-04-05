package ru.nsu.reciepebook.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.google.gson.Gson
import ru.nsu.reciepebook.service.CountdownService
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.navigation.screens.favorite
import ru.nsu.reciepebook.ui.navigation.screens.myRecipes
import ru.nsu.reciepebook.ui.navigation.screens.profileScreen
import ru.nsu.reciepebook.ui.navigation.screens.recipeInfo
import ru.nsu.reciepebook.ui.navigation.screens.searchFilter
import ru.nsu.reciepebook.util.Constants


fun NavGraphBuilder.profileGraph(
    navController: NavHostController,
    countdownService: CountdownService
) {
    navigation(
        startDestination = Screen.ProfileScreen.route,
        route = Graph.ProfileGraph.route
    ) {
        profileScreen(navController)
        favorite(navController, countdownService)
        myRecipes(navController)
        searchFilter(navController,
            Screen.FilterScreenMyRecipe.route
        ) {
            navController.navigate(Screen.MyRecipesScreen.route + "?${Constants.TAGS_ARG}=${Gson().toJson(it)}") {
                popUpTo(Screen.MyRecipesScreen.route)
                launchSingleTop = true
                restoreState = true
            }
        }
        addRecipeGraph(navController)
        recipeInfo(navController)
    }
}
