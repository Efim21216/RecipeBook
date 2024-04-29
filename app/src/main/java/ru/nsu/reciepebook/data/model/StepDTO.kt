package ru.nsu.reciepebook.data.model

data class StepDTO(
    val number: Int,
    val recipeId: Int? = null,
    val id: Int? = null,
    val name: String,
    val description: String,
    val timerInSeconds: Int,
    val imageId: Int? = null
)