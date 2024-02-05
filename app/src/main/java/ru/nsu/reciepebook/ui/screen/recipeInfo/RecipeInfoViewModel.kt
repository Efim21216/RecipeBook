package ru.nsu.reciepebook.ui.screen.recipeInfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.nsu.reciepebook.util.Constants.Companion.RECIPE_ID

class RecipeInfoViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(RecipeInfoState())
    val uiState: StateFlow<RecipeInfoState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    val recipeId = savedStateHandle.getStateFlow(RECIPE_ID, -1)
    fun onEvent(event: RecipeInfoEvent) {

    }
    sealed class UIEvent {

    }
}