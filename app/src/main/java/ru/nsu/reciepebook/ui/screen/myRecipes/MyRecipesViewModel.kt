package ru.nsu.reciepebook.ui.screen.myRecipes

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import ru.nsu.reciepebook.util.Constants.Companion.TAGS_ARG

class MyRecipesViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(MyRecipesState())
    val uiState: StateFlow<MyRecipesState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val tags = savedStateHandle.getStateFlow(TAGS_ARG, emptyArray<String>())
    fun setTags(tags: Array<String>?) {
        savedStateHandle[TAGS_ARG] = tags
    }
    fun onEvent(event: MyRecipesEvent) {

        when (event) {
            is MyRecipesEvent.OnChangeName -> {
                _uiState.update {
                    it.copy(name = event.value)
                }
            }

            MyRecipesEvent.OnDone -> TODO()
        }
    }
    sealed class UIEvent {

    }
}