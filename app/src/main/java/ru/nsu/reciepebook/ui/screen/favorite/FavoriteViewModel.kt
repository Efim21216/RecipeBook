package ru.nsu.reciepebook.ui.screen.favorite

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FavoriteViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteState())
    val uiState: StateFlow<FavoriteState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: FavoriteEvent) {

    }
    sealed class UIEvent {

    }
}
