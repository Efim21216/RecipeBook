package ru.nsu.reciepebook.ui.navigation.screens

import android.os.Bundle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.screen.myRecipes.MyRecipes
import ru.nsu.reciepebook.ui.screen.myRecipes.MyRecipesViewModel
import ru.nsu.reciepebook.util.Constants
import ru.nsu.reciepebook.util.Constants.Companion.TAGS_ARG

fun NavGraphBuilder.myRecipes(
    navController: NavHostController
) {
    composable(
        route = Screen.MyRecipesScreen.route + "?$TAGS_ARG={$TAGS_ARG}",
        arguments = listOf(
            navArgument(name = TAGS_ARG) {
                type = StringArrayType
                defaultValue = emptyArray<String>()
            }
        )
    ) {
        val viewModel = hiltViewModel<MyRecipesViewModel>()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        if (it.arguments?.getStringArray(TAGS_ARG) != null)
            viewModel.setTags(it.arguments?.getStringArray(TAGS_ARG)!!)
        MyRecipes(
            uiState = uiState.value,
            onEvent = viewModel::onEvent,
            uiEvent = viewModel.uiEvent,
            toRecipeInfo = {id ->
                navController.navigate(Screen.RecipeInfoScreen.route + "?${Constants.RECIPE_ID_ARG}=$id")
            },
            navigateUp = { navController.navigateUp() },
            toFilter = { navController.navigate(Screen.FilterScreenMyRecipe.route) }
        )
    }
    recipeInfo(navController)
}


val StringArrayType: NavType<Array<String>?> = object: NavType<Array<String>?>(true) {
    override fun get(bundle: Bundle, key: String): Array<String> {
        if (bundle.getStringArray(key) == null)
            return emptyArray()
        return bundle.getStringArray(key)!!
    }

    override fun parseValue(value: String): Array<String> {
        return Gson().fromJson(value, Array<String>::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Array<String>?) {
        bundle.putStringArray(key, value)
    }
}