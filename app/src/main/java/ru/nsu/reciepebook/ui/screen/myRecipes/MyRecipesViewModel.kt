package ru.nsu.reciepebook.ui.screen.myRecipes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class MyRecipesViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(MyRecipesState())
    val uiState: StateFlow<MyRecipesState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: MyRecipesEvent) {

    }
    sealed class UIEvent {

    }
}