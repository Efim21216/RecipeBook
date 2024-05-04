package ru.nsu.reciepebook.ui.screen.filter

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
class SearchFilterViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(SearchFilterState())
    val uiState: StateFlow<SearchFilterState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(event: SearchFilterEvent) {
        when (event) {
            is SearchFilterEvent.OnAddTag -> _uiState.update {
                val allTags = it.allTags.minus(event.value)
                it.copy(
                    displayedTags = it.displayedTags.plus(event.value.substring(1)),
                    suggestedTags = allTags.take(5),
                    allTags = allTags,
                    tagInput = "")
            }
            is SearchFilterEvent.OnChangeInputTag -> _uiState.update {
                it.copy(tagInput = event.value,
                    suggestedTags = it.allTags.filter {tag ->
                        tag.startsWith("#" + event.value)
                    }.take(5))
            }
            SearchFilterEvent.OnClearTag -> _uiState.update {
                _uiState.value.copy(tagInput = "", suggestedTags = it.allTags.take(5))
            }
            is SearchFilterEvent.OnRemoveTag -> _uiState.update {
                _uiState.value.copy(displayedTags = _uiState.value.displayedTags.minus(event.value),
                    allTags = it.allTags.plus("#"+event.value))
            }
        }
    }
    sealed class UIEvent {

    }
}