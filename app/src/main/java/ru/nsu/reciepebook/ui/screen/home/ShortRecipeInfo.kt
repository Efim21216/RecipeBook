package ru.nsu.reciepebook.ui.screen.home

data class ShortRecipeInfo(
    val title: String = "Название рецепта",
    val previewImage: String = "",
    val profileImage: String = "",
    val author: String = "Автор",
    val description: String = "Описание",
    val id: Int = -1
)