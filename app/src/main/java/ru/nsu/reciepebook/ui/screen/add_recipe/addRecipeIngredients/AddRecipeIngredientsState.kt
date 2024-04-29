package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients

data class Ingredient(
    val name: String,
    val quantity: Float,
    val measure: Int
)

data class AddRecipeIngredientsState(
    val ingredients: List<Ingredient> = listOf()
)
