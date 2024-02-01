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
import ru.nsu.reciepebook.ui.state.TextFieldState
import ru.nsu.reciepebook.util.Resource
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    val registerUseCase: RegisterUseCase
): ViewModel() {
    private val _email = mutableStateOf(TextFieldState(text = "", hint = "Введите почту"))
    private val _password = mutableStateOf(TextFieldState(text = "", hint = "Введите пароль"))
    val email: State<TextFieldState> = _email
    val password: State<TextFieldState> = _password

    private val _uiEvent =  Channel<UIEvent>()
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
        }
    }
    private fun register() {
        registerUseCase(User(
            email = email.value.text,
            password = password.value.text
        )).onEach { result ->
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