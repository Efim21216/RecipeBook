package ru.nsu.reciepebook.util

sealed class UiEvent {
    data class Navigate(val route: String): UiEvent()
    data class PopUpTo(val route: String, val from: String): UiEvent()
    data class ShowSnackBar(
        val message: String,
        val action: String? = null
    ): UiEvent()
}