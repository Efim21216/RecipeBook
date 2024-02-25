package ru.nsu.reciepebook.ui.screen.add_recipe

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.AddRecipeInfoEvent
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.AddRecipeInfoState
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.AddRecipeIngredientsEvent
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.AddRecipeIngredientsState
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep.AddRecipeStepEvent
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep.AddRecipeStepState
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(): ViewModel() {
    private val _uiStateInfo = MutableStateFlow(AddRecipeInfoState())
    val uiStateInfo: StateFlow<AddRecipeInfoState> = _uiStateInfo.asStateFlow()
    private val _uiEventInfo = Channel<UIEventInfo>()
    val uiEventInfo = _uiEventInfo.receiveAsFlow()

    private val _uiStateStep = MutableStateFlow(AddRecipeStepState())
    val uiStateStep: StateFlow<AddRecipeStepState> = _uiStateStep.asStateFlow()
    private val _uiEventStep = Channel<UIEventStep>()
    val uiEventStep = _uiEventStep.receiveAsFlow()

    private val _uiStateIngredients = MutableStateFlow(AddRecipeIngredientsState())
    val uiStateIngredients: StateFlow<AddRecipeIngredientsState> = _uiStateIngredients.asStateFlow()
    private val _uiEventIngredients = Channel<UIEventIngredients>()
    val uiEventIngredients = _uiEventIngredients.receiveAsFlow()
    var shareId by mutableIntStateOf(-1)
        private set
    fun onEventInfo(event: AddRecipeInfoEvent) {
        when (event) {
            is AddRecipeInfoEvent.OnChangeName -> _uiStateInfo.update {
                _uiStateInfo.value.copy(name = event.value)
            }

            is AddRecipeInfoEvent.OnChangeDescription -> _uiStateInfo.update {
                _uiStateInfo.value.copy(description = event.value)
            }

            is AddRecipeInfoEvent.OnChangeTime -> _uiStateInfo.update {
                _uiStateInfo.value.copy(timeInSeconds = event.value)
            }
        }
    }
    fun onEventStep(event: AddRecipeStepEvent) {

    }
    fun onEventIngredients(event: AddRecipeIngredientsEvent) {

    }
    sealed class UIEventInfo {

    }
    sealed class UIEventStep {

    }
    sealed class UIEventIngredients {

    }
}