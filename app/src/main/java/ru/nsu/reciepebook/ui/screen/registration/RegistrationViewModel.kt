package ru.nsu.reciepebook.ui.screen.registration


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.nsu.reciepebook.data.model.User
import ru.nsu.reciepebook.data.use_cases.registration.RegisterUseCase
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.state.TextFieldState
import ru.nsu.reciepebook.util.Resource
import ru.nsu.reciepebook.util.UiEvent
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    val registerUseCase: RegisterUseCase
): ViewModel() {
    private val _email = mutableStateOf(TextFieldState(text = "", hint = "Введите почту"))
    private val _password = mutableStateOf(TextFieldState(text = "", hint = "Введите пароль"))
    val email: State<TextFieldState> = _email
    val password: State<TextFieldState> = _password

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnChangeEmail -> {
                _email.value = email.value.copy(
                    text = event.value
                )
            }

            is RegistrationEvent.OnChangePassword -> {
                _password.value = password.value.copy(
                    text = event.value
                )
            }
            RegistrationEvent.Register -> {
                register()
            }

            RegistrationEvent.ToAuth -> sendUiEvent(UiEvent.Navigate(Screen.AuthorizationScreen.route))
        }
    }
    private fun register() {
        registerUseCase(User(
            email = email.value.text,
            password = password.value.text
        )).onEach { result ->
            when (result) {
                is Resource.Error -> sendUiEvent(UiEvent.ShowSnackBar(message = result.message ?: "Ошибка"))
                is Resource.Loading -> Unit
                is Resource.Success -> sendUiEvent(UiEvent.Navigate(Screen.SecretScreen.route))
            }
        }.launchIn(viewModelScope)
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}