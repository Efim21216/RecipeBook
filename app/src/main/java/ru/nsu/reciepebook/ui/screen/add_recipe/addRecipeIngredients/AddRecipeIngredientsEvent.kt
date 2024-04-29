package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients

sealed class AddRecipeIngredientsEvent {
        data class IngredientChanged(val value: Ingredient, val id: Int) : AddRecipeIngredientsEvent()
        data class DeleteIngredient(val id: Int) : AddRecipeIngredientsEvent()
        data object AddIngredient : AddRecipeIngredientsEvent()
        data object AddFirstStep: AddRecipeIngredientsEvent()
}