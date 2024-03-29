package ru.nsu.reciepebook.ui.screen.cooking

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class CookingViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(CookingState())
    val uiState: StateFlow<CookingState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: CookingEvent) {

    }
    sealed class UIEvent {

    }
}