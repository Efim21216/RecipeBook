package ru.nsu.reciepebook

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
import ru.nsu.reciepebook.data.use_cases.AuthenticateUseCase
import ru.nsu.reciepebook.ui.Graph
import ru.nsu.reciepebook.ui.screen.cooking.CookingViewModel
import ru.nsu.reciepebook.util.StartEvent
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    authenticateUseCase: AuthenticateUseCase
): ViewModel() {

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading
    private val _startScreen = mutableStateOf(Graph.AuthGraph.route)
    val startScreen: State<String> = _startScreen
    private val _navEvent = Channel<UIEvent>()
    val navEvent = _navEvent.receiveAsFlow()

    init {
        authenticateUseCase().onEach {result ->
            when (result) {
                StartEvent.End -> _isLoading.value = false
                StartEvent.Success -> _startScreen.value = Graph.MainGraph.route
            }
        }.launchIn(viewModelScope)
    }
    fun navigate(to: String) {
        viewModelScope.launch {
            _navEvent.send(UIEvent.Navigate(to))
        }
    }

    sealed class UIEvent {
        data class Navigate(val to: String): UIEvent()
    }
}