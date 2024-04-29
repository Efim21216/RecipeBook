package ru.nsu.reciepebook.ui.screen.cooking

data class CookingState(
    val steps: List<StepState> = emptyList(),
    val currentStep: Int = -1,
    val token: String = ""
)