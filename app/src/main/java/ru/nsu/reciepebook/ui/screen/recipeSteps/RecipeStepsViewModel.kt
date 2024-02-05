package ru.nsu.reciepebook.ui.screen.recipeSteps

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class RecipeStepsViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(RecipeStepsState())
    val uiState: StateFlow<RecipeStepsState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: RecipeStepsEvent) {

    }
    sealed class UIEvent {

    }
}