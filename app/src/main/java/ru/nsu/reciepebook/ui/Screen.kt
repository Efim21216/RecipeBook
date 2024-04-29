package ru.nsu.reciepebook.ui

sealed class Screen(val route: String) {
    data object AuthorizationScreen: Screen(route = "auth_screen")
    data object RegistrationScreen: Screen(route = "reg_screen")
    data object HomeScreen: Screen(route = "home_screen")
    data object ProfileScreen: Screen(route = "profile_screen")
    data object SearchScreen: Screen(route = "search_screen")
    data object FilterScreen: Screen(route = "filter_screen")
    data object FilterScreenMyRecipe: Screen(route = "filter_screen_my_recipe")
    data object FavoriteScreen: Screen(route = "favorite_screen")
    data object SearchResultsScreen: Screen(route = "search_results_screen")
    data object CookingScreen: Screen(route = "cooking_screen")
    data object CookingInfoScreen: Screen(route = "cooking_info_screen")
    data object MyRecipesScreen: Screen(route = "my_recipes_screen")
    data object RecipeInfoScreen: Screen(route = "recipe_info_screen")
    data object RecipeStepsScreen: Screen(route = "recipe_steps_screen")
    data object AddRecipeStepScreen: Screen(route = "add_recipe_step_screen")
    data object AddRecipeInfoScreen: Screen(route = "add_recipe_info_screen")
    data object AddRecipeIngredientsScreen: Screen(route = "add_recipe_ingredients_screen")

}
sealed class Graph(val route: String) {
    data object AuthGraph: Screen(route = "auth_graph")
    data object MainGraph: Screen(route = "main_graph")
    data object HomeGraph: Screen(route = "home_graph")
    data object ProfileGraph: Screen(route = "profile_graph")
    data object SearchGraph: Screen(route = "search_graph")
    data object AddRecipeGraph: Screen(route = "add_recipe_graph")
    data object CookingGraph: Screen(route = "cooking_graph")
}