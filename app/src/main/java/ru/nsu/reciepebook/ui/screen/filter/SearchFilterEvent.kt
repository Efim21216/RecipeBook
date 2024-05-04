package ru.nsu.reciepebook.ui.screen.filter

sealed class SearchFilterEvent {
    data class OnChangeInputTag(val value: String): SearchFilterEvent()
    data class OnAddTag(val value: String): SearchFilterEvent()
    data class OnRemoveTag(val value: String): SearchFilterEvent()
    data object OnClearTag: SearchFilterEvent()
}