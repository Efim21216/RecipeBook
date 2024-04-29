package ru.nsu.reciepebook.ui.screen.cooking

import android.util.Log
import androidx.lifecycle.SavedStateHandle
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
import ru.nsu.reciepebook.data.use_cases.GetRecipeInfoUseCase
import ru.nsu.reciepebook.data.use_cases.GetRecipeStepsUseCase
import ru.nsu.reciepebook.service.CountdownService
import ru.nsu.reciepebook.service.CountdownState
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.Ingredient
import ru.nsu.reciepebook.util.Constants
import ru.nsu.reciepebook.util.Constants.Companion.BASE_URL
import ru.nsu.reciepebook.util.JwtTokenManager
import ru.nsu.reciepebook.util.Resource
import javax.inject.Inject

@HiltViewModel
class CookingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getRecipeInfoUseCase: GetRecipeInfoUseCase,
    val getRecipeStepsUseCase: GetRecipeStepsUseCase,
    val jwtTokenManager: JwtTokenManager,
): ViewModel() {
    private val _uiState = MutableStateFlow(CookingState())
    val uiState: StateFlow<CookingState> = _uiState.asStateFlow()
    private val _uiStateInfo = MutableStateFlow(CookingInfoState())
    val uiStateInfo: StateFlow<CookingInfoState> = _uiStateInfo.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    private val recipeId = savedStateHandle.getStateFlow(Constants.RECIPE_ID_ARG, -1)
    init {
        getRecipeInfoUseCase(recipeId.value).onEach {
            when (it) {
                is Resource.Error -> Log.d("MyTag", "Error - ${it.message}")
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    _uiStateInfo.update {_ ->
                        with (it.data!!) {
                            CookingInfoState(name = name,
                                description = description,
                                ingredients = ingredients.map {dto ->
                                    Ingredient(dto.name, dto.weight, Constants.Measures.valueOf(dto.countUnit).ordinal)
                                },
                                imageUrl = "${BASE_URL}/recipe/$id/image",
                                token = Constants.AUTH + jwtTokenManager.getAccessJwt()!!
                            )
                        }
                    }
                    getAllSteps()
                }
            }
        }.launchIn(viewModelScope)

    }
    private fun getAllSteps() {
        getRecipeStepsUseCase(recipeId.value).onEach {
            when (it) {
                is Resource.Error -> Log.d("MyTag", "Error - ${it.message}")
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    _uiState.update {_ ->
                        CookingState(
                            steps = it.data!!.map { dto ->
                                StepState(
                                    number = dto.number,
                                    name = dto.name,
                                    duration = dto.timerInSeconds,
                                    text = dto.description,
                                    imageUrl = if (dto.imageId != null) "$BASE_URL/recipe/${recipeId.value}/step/${dto
                                        .number}/image" else null
                                )
                            },
                            token = Constants.AUTH + jwtTokenManager.getAccessJwt()!!
                        )
                    }
                    _uiStateInfo.update { state ->
                        state.copy(
                            numberOfSteps = it.data!!.size
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
    fun initStep(countdownService: CountdownService, stepNumber: Int) {
        if (_uiState.value.currentStep == -1)
            setStep(countdownService, stepNumber)
    }
    fun setStep(countdownService: CountdownService, stepNumber: Int) {
        val size = uiState.value.steps.size
        if (size > stepNumber) {
            _uiState.update {
                it.copy(currentStep = stepNumber)
            }
        }
        if (countdownService.timerState.value.state == CountdownState.Idle) {
            if (size <= stepNumber) {
                countdownService.setTime(0)
            } else {
                countdownService.setTime(uiState.value
                    .steps[stepNumber].duration)
            }
        }
    }
    fun onEvent(event: CookingEvent) {

    }
    sealed class UIEvent {

    }
}