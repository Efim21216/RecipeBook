package ru.nsu.reciepebook.ui.screen.add_recipe

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.nsu.reciepebook.data.use_cases.ConfirmRecipeUseCase
import ru.nsu.reciepebook.data.use_cases.CreateRecipeUseCase
import ru.nsu.reciepebook.data.use_cases.CreateStepUseCase
import ru.nsu.reciepebook.data.use_cases.UploadRecipeImageUseCase
import ru.nsu.reciepebook.data.use_cases.UploadStepImageUseCase
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.AddRecipeInfoEvent
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.AddRecipeInfoState
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.AddRecipeIngredientsEvent
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.AddRecipeIngredientsState
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.Ingredient
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep.AddRecipeStepEvent
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep.AddRecipeStepState
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep.StepInfo
import ru.nsu.reciepebook.ui.screen.authorization.AuthorizationViewModel
import ru.nsu.reciepebook.util.Constants.Companion.ALL_TAGS
import ru.nsu.reciepebook.util.Resource
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    val createRecipeUseCase: CreateRecipeUseCase,
    val uploadRecipeImageUseCase: UploadRecipeImageUseCase,
    val createStepUseCase: CreateStepUseCase,
    val uploadStepImageUseCase: UploadStepImageUseCase,
    val confirmRecipeUseCase: ConfirmRecipeUseCase
): ViewModel() {
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

            is AddRecipeInfoEvent.OnChangeComplexity -> _uiStateInfo.update {
                _uiStateInfo.value.copy(selectedIndexComplexity = event.value)
            }
            is AddRecipeInfoEvent.OnChangeKcal -> _uiStateInfo.update {
                _uiStateInfo.value.copy(kcal = event.value)
            }
            is AddRecipeInfoEvent.OnChangeType -> _uiStateInfo.update {
                _uiStateInfo.value.copy(selectedIndexType = event.value)
            }
            is AddRecipeInfoEvent.OnAddTag -> _uiStateInfo.update {
                _uiStateInfo.value.copy(
                    displayedTags = _uiStateInfo.value.displayedTags.plus(event.value),
                    suggestedTags = ALL_TAGS.take(5),
                    tagInput = "")
            }
            is AddRecipeInfoEvent.OnChangeInputTag -> _uiStateInfo.update {
                _uiStateInfo.value.copy(tagInput = event.value,
                    suggestedTags = ALL_TAGS.filter { it.startsWith("#" + event.value) }.take(5))
            }
            is AddRecipeInfoEvent.OnClearTag -> _uiStateInfo.update {
                _uiStateInfo.value.copy(tagInput = "")
            }
            is AddRecipeInfoEvent.OnRemoveTag -> _uiStateInfo.update {
                _uiStateInfo.value.copy(displayedTags = _uiStateInfo.value.displayedTags.minus(event.value))
            }

            is AddRecipeInfoEvent.OnImageChange -> _uiStateInfo.update {
                _uiStateInfo.value.copy(selectedImageUri = event.value)
            }

        }
    }
    fun onEventStep(event: AddRecipeStepEvent) {
        when (event) {
            AddRecipeStepEvent.AddStep -> {
                createStep()
                _uiStateStep.update {
                    it.copy(currentStep = it.steps.size, steps = it.steps + StepInfo())
                }
            }
            is AddRecipeStepEvent.OnChangeDescription -> _uiStateStep.update {
                it.copy(steps = it.steps.update(
                    it.currentStep,
                    it.steps[it.currentStep].copy(description = event.value)
                    ) )
            }
            is AddRecipeStepEvent.OnChangeName -> _uiStateStep.update {
                it.copy(steps = it.steps.update(
                    it.currentStep,
                    it.steps[it.currentStep].copy(name = event.value)
                ) )
            }
            is AddRecipeStepEvent.OnChangeTime -> _uiStateStep.update {
                it.copy(steps = it.steps.update(
                    it.currentStep,
                    it.steps[it.currentStep].copy(timeInSeconds = event.value)
                ) )
            }
            is AddRecipeStepEvent.OnImageChange -> _uiStateStep.update {
                it.copy(steps = it.steps.update(
                    it.currentStep,
                    it.steps[it.currentStep].copy(selectedImageUri = event.value)
                ) )
            }
            is AddRecipeStepEvent.ToStep -> _uiStateStep.update {
                it.copy(currentStep = event.value)
            }
            AddRecipeStepEvent.OnDone -> confirmRecipe()
        }
    }
    fun onEventIngredients(event: AddRecipeIngredientsEvent) {
        when (event) {
            is AddRecipeIngredientsEvent.DeleteIngredient -> _uiStateIngredients.update {
                _uiStateIngredients.value.copy(ingredients = it.ingredients.filterIndexed{idx, _ ->
                    idx != event.id
                })
            }
            is AddRecipeIngredientsEvent.IngredientChanged -> _uiStateIngredients.update { state ->
                _uiStateIngredients.value.copy(ingredients = state.ingredients.mapIndexed {idx, it ->
                    if (idx == event.id)
                        event.value
                    else
                        it
                })
            }

            AddRecipeIngredientsEvent.AddIngredient -> _uiStateIngredients.update {
                _uiStateIngredients.value.copy(ingredients = it.ingredients.plus(Ingredient("", 0f, 1)))
            }

            AddRecipeIngredientsEvent.AddFirstStep -> {
                if (_uiStateStep.value.steps.isEmpty()) {
                    _uiStateStep.update {
                        _uiStateStep.value.copy(
                            steps = listOf(StepInfo())
                        )
                    }
                    createRecipe()
                }
            }
        }
    }
    private fun sendUiEvent(event: UIEventStep) {
        viewModelScope.launch {
            _uiEventStep.send(event)
        }
    }
    private fun confirmRecipe() {
        createStep {
            confirmRecipeUseCase(shareId).onEach { result ->
                when (result) {
                    is Resource.Error -> Log.d("MyTag", "Error - ${result.message}")
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        Log.d("MyTag", "SEND TO RECIPE")
                        sendUiEvent(UIEventStep.ToRecipe(shareId))
                    }
                }

            }.launchIn(viewModelScope)
        }
    }
    private fun createRecipe() {
        createRecipeUseCase(_uiStateInfo.value, _uiStateIngredients.value)
            .onEach {result ->
                when (result) {
                    is Resource.Error -> Log.d("MyTag", "Error - ${result.message}")
                    is Resource.Loading -> Unit
                    is Resource.Success -> {
                        shareId = result.data!!.id!!
                        uploadRecipeImage()
                    }
                }
            }.launchIn(viewModelScope)
    }
    private fun createStep(callback: () -> Unit = {}) {
        val number: Int = _uiStateStep.value.currentStep
        Log.d("MyTag", "CREATE STEP")
        createStepUseCase(_uiStateStep.value, shareId).onEach { result ->
            when (result) {
                is Resource.Error -> Log.d("MyTag", "Error - ${result.message}")
                is Resource.Loading -> Unit
                is Resource.Success -> uploadStepImage(number, callback)
            }

        }.launchIn(viewModelScope)
    }
    private fun uploadStepImage(number: Int, callback: () -> Unit = {}) {
        val uri = _uiStateStep.value.steps[number].selectedImageUri
        Log.d("MyTag", "number - $number")
        if (uri == null) {
            callback()
            return
        }
        uri.toFile().let {
            it.deleteOnExit()
            uploadStepImageUseCase(image = it,
                recipeId = shareId,
                number = number).onEach {result ->
                when (result) {
                    is Resource.Error -> Log.d("MyTag", "Error - ${result.message}")
                    is Resource.Loading -> Unit
                    is Resource.Success -> callback()
                }

            }.launchIn(viewModelScope)
        }
    }
    private fun uploadRecipeImage() {
        if (uiStateInfo.value.selectedImageUri == null)
            return
        _uiStateInfo.value.selectedImageUri!!.toFile().let {
            it.deleteOnExit()
            uploadRecipeImageUseCase(image = it,
                recipeId = shareId).onEach {result ->
                when (result) {
                    is Resource.Error -> Log.d("MyTag", "Error in uploading - ${result.message}")
                    is Resource.Loading -> Unit
                    is Resource.Success -> Unit
                }

            }.launchIn(viewModelScope)
        }
    }
    sealed class UIEventInfo {

    }
    sealed class UIEventStep {
        data class ToRecipe(val recipeId: Int): UIEventStep()
    }
    sealed class UIEventIngredients {

    }
}
fun <T> List<T>.update(index: Int, item: T): List<T> = toMutableList().apply { this[index] = item }