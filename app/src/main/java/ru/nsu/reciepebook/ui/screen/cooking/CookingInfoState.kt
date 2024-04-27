package ru.nsu.reciepebook.ui.screen.cooking

import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.Ingredient

data class CookingInfoState(
 val name: String = "",
 val imageUrl:String = "",
 val numberOfSteps: Int = 5,
 val description: String = "",
 val ingredients: List<Ingredient> = listOf(),
)