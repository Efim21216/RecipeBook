package ru.nsu.reciepebook.ui.screen.cooking

data class StepState(
    val number: Int = -1,
    val duration: Int = 0,
    val imageUrl: String? = null,
    val text: String = "",
    val name: String = "",
)