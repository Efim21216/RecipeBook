package ru.nsu.reciepebook.ui.screen.recipeInfo

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
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.Ingredient
import ru.nsu.reciepebook.util.Constants
import ru.nsu.reciepebook.util.Constants.Companion.BASE_URL
import ru.nsu.reciepebook.util.Constants.Companion.RECIPE_ID_ARG
import ru.nsu.reciepebook.util.JwtTokenManager
import ru.nsu.reciepebook.util.Resource
import javax.inject.Inject

@HiltViewModel
class RecipeInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getRecipeInfoUseCase: GetRecipeInfoUseCase,
    jwtTokenManager: JwtTokenManager
): ViewModel() {
    private val _uiState = MutableStateFlow(RecipeInfoState())
    val uiState: StateFlow<RecipeInfoState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    val recipeId = savedStateHandle.getStateFlow(RECIPE_ID_ARG, -1)
    init {
        getRecipeInfoUseCase(recipeId.value).onEach {
            when (it) {
                is Resource.Error -> Log.d("MyTag", "Error - ${it.message}")
                is Resource.Loading -> Unit
                is Resource.Success -> _uiState.update {_ ->
                    with (it.data!!) {
                        RecipeInfoState(name = name,
                            author = "Ann",
                            description = description,
                            ingredients = ingredients.map {dto ->
                                Ingredient(dto.name, dto.weight, Constants.Measures.valueOf(dto.countUnit).ordinal)
                            },
                            imageUrl = "$BASE_URL/recipe/$id/image",
                            token = Constants.AUTH + jwtTokenManager.getAccessJwt()!!
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
    fun onEvent(event: RecipeInfoEvent) {

    }
    sealed class UIEvent {

    }
}