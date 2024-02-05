package ru.nsu.reciepebook.ui.screen.filter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class SearchFilterViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(SearchFilterState())
    val uiState: StateFlow<SearchFilterState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: SearchFilterEvent) {

    }
    sealed class UIEvent {

    }
}