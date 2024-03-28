package ru.nsu.reciepebook.data.model
/*
{
      "id": 0,
      "name": "string",
      "weight": 0,
      "countUnit": "kilogram"
    }
 */
data class IngredientDTO(
    val name: String,
    val weight: Float,
    val countUnit: String
)