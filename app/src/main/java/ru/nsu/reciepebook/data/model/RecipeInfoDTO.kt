package ru.nsu.reciepebook.data.model

data class RecipeInfoDTO(
    val name: String,
    val description: String,
    val ingredients: List<IngredientDTO>
)