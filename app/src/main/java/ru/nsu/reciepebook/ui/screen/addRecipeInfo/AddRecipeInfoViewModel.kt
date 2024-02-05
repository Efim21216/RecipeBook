package ru.nsu.reciepebook.ui.screen.addRecipeInfo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class AddRecipeInfoViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(AddRecipeInfoState())
    val uiState: StateFlow<AddRecipeInfoState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: AddRecipeInfoEvent) {

    }
    sealed class UIEvent {

    }
}