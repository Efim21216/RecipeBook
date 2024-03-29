package ru.nsu.reciepebook.ui.screen.search

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class SearchViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnChangeName -> _uiState.update {
                _uiState.value.copy(name = event.value)
            }
        }
    }
    sealed class UIEvent {

    }
}