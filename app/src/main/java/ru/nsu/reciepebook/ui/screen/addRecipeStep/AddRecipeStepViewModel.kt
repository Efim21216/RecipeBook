package ru.nsu.reciepebook.ui.screen.addRecipeStep

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class AddRecipeStepViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(AddRecipeStepState())
    val uiState: StateFlow<AddRecipeStepState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: AddRecipeStepEvent) {

    }
    sealed class UIEvent {

    }
}