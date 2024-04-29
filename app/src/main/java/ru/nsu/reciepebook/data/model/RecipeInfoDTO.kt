package ru.nsu.reciepebook.data.model

data class RecipeInfoDTO(
    val id: Int? = null,
    val imageId: Int? = null,
    val name: String,
    val description: String,
    val ingredients: List<IngredientDTO>
)