package ru.nsu.reciepebook.ui.screen.myRecipes

import ru.nsu.reciepebook.ui.screen.home.ShortRecipeInfo

data class MyRecipesState(
    val name: String = "",
    val token: String = "",
    val recipes: List<ShortRecipeInfo> = listOf(
        ShortRecipeInfo(description =
        "Описание рецепта. Первый шаг, второй шаг, третий шаг, четвёртый шаг, пятый шаг, шестой шаг, седьмой шаг"),
        ShortRecipeInfo(title = "Очень длинное название рецепта")
    )
)