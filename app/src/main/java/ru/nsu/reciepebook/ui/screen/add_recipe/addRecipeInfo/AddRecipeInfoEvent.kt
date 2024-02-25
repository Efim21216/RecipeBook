package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

sealed class AddRecipeInfoEvent {
    data class OnChangeName(val value: String): AddRecipeInfoEvent();
    data class OnChangeDescription(val value: String): AddRecipeInfoEvent();
    data class OnChangeTime(val value: Long): AddRecipeInfoEvent();
}