package ru.nsu.reciepebook.ui.screen.addRecipeIngredients

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class AddRecipeIngredientsViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(AddRecipeIngredientsState())
    val uiState: StateFlow<AddRecipeIngredientsState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: AddRecipeIngredientsEvent) {

    }
    sealed class UIEvent {

    }
}