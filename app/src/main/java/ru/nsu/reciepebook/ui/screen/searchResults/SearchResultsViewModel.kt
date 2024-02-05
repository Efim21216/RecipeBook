package ru.nsu.reciepebook.ui.screen.searchResults

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class SearchResultsViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(SearchResultsState())
    val uiState: StateFlow<SearchResultsState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: SearchResultsEvent) {

    }
    sealed class UIEvent {

    }
}