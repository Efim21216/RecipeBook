package ru.nsu.reciepebook.ui.screen.authorization

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
import ru.nsu.reciepebook.data.model.User
import ru.nsu.reciepebook.data.use_cases.LoginUseCase
import ru.nsu.reciepebook.util.Resource
import javax.inject.Inject
@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    val loginUseCase: LoginUseCase
): ViewModel() {


    private val _uiState = MutableStateFlow(AuthState(email = "", password = ""))
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AuthorizationEvent) {
        when (event) {
            is AuthorizationEvent.OnChangeEmail -> _uiState.update {
                _uiState.value.copy(email=event.value)
            }
            is AuthorizationEvent.OnChangePassword -> _uiState.update {
                _uiState.value.copy(password=event.value)
            }
            AuthorizationEvent.Authorize -> login()
        }
    }
    private fun login() {
        loginUseCase(
            User(
            email = uiState.value.email,
            password = uiState.value.password
        )
        ).onEach { result ->
            when (result) {
                is Resource.Error -> sendUiEvent(UIEvent.ShowSnackBar(message = result.message ?: "Ошибка"))
                is Resource.Loading -> Unit
                is Resource.Success -> sendUiEvent(UIEvent.ToMain)
            }
        }.launchIn(viewModelScope)
    }
    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    sealed class UIEvent {
        data object ToMain: UIEvent()
        data class ShowSnackBar(
            val message: String,
            val action: String? = null
        ): UIEvent()
    }
}