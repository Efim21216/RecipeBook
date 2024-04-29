package ru.nsu.reciepebook.ui.screen.home

import android.util.Log
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
import ru.nsu.reciepebook.data.use_cases.GetMyRecipesUseCase
import ru.nsu.reciepebook.util.Constants
import ru.nsu.reciepebook.util.JwtTokenManager
import ru.nsu.reciepebook.util.Resource
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    jwtTokenManager: JwtTokenManager,
    getMyRecipesUseCase: GetMyRecipesUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getMyRecipesUseCase().onEach {
            when (it) {
                is Resource.Error -> Log.d("MyTag", "Error - ${it.message}")
                is Resource.Loading -> Unit
                is Resource.Success -> _uiState.update { state ->
                    state.copy(
                        recipes = it.data!!.map { recipe ->
                            ShortRecipeInfo(
                                title = recipe.name,
                                description = recipe.description,
                                previewImage = "${Constants.BASE_URL}/recipe/${recipe.id}/image",
                                id = recipe.id ?: -1
                            )
                        },
                        token = Constants.AUTH + jwtTokenManager.getAccessJwt()!!
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: HomeEvent) {

    }

    sealed class UIEvent {

    }
}