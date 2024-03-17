package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients

data class Ingredient(
    val name: String,
    val quantity: String,
    val quantityType: String
)

data class AddRecipeIngredientsState(
    val ingredients: List<Ingredient> = listOf(),
    /*val newIngredientName: String = "",
    val newIngredientQuantity: String = "",
    val isDialogOpen: Boolean = false*/
)
