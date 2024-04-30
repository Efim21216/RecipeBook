package ru.nsu.reciepebook.ui.screen.search

sealed class SearchEvent {
    data class OnChangeName(val value: String): SearchEvent()
    data object OnDone: SearchEvent()
}