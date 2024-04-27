package ru.nsu.reciepebook.ui.screen.recipeInfo

import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.Ingredient

data class Ingredient(
    val name: String,
    val quantity: Float,
    val measure: Int
)
data class RecipeInfoState (
    val name: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val favourite: Int = 0,
    val favouritesString: String = "",
    val ingredients: List<Ingredient> = listOf(),
    val profileImage: String = "",
    val  author: String = "",
)