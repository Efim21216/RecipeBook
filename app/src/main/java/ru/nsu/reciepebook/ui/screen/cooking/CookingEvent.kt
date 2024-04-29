package ru.nsu.reciepebook.ui.screen.cooking

sealed class CookingEvent {
    data class GetRecipeInfo(val recipeId: Int): CookingEvent()
}