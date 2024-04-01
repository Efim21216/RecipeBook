package ru.nsu.reciepebook.ui.screen.myRecipes

sealed class MyRecipesEvent {
    data class OnChangeName(val value: String): MyRecipesEvent()
    data object OnDone: MyRecipesEvent()
}