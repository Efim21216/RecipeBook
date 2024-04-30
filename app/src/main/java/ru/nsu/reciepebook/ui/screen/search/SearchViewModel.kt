package ru.nsu.reciepebook.ui.screen.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import ru.nsu.reciepebook.util.Constants
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val tags = savedStateHandle.getStateFlow(Constants.TAGS_ARG, emptyArray<String>())
    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnChangeName -> _uiState.update {
                _uiState.value.copy(name = event.value)
            }

            SearchEvent.OnDone -> Log.d("MyTag", "tags size - ${tags.value.size}")
        }
    }
    fun setTags(tags: Array<String>) {
        savedStateHandle[Constants.TAGS_ARG] = tags
    }
    sealed class UIEvent {

    }
}