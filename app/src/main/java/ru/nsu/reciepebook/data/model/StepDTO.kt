package ru.nsu.reciepebook.data.model

data class StepDTO(
    val number: Int,
    val recipeId: Int,
    val name: String,
    val description: String,
    val timerInSeconds: Long
)