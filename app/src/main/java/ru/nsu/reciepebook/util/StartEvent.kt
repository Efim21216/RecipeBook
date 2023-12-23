package ru.nsu.reciepebook.util

sealed class StartEvent {
    data object Success: StartEvent()
    data object End: StartEvent()

}