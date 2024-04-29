package ru.nsu.reciepebook.ui.screen.cooking

import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.Ingredient

data class CookingInfoState(
 val name: String = "",
 val imageUrl:String = "",
 val token: String = "",
 val numberOfSteps: Int = 1,
 val description: String = "",
 val id: Int = -1,
 val ingredients: List<Ingredient> = listOf(),
)