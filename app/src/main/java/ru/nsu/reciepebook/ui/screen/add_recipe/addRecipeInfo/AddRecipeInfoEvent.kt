package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

sealed class AddRecipeInfoEvent {
    class AddId(val id: Int): AddRecipeInfoEvent()
}