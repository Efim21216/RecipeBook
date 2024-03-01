package ru.nsu.reciepebook.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.TopBar
import ru.nsu.reciepebook.ui.theme.ReciepeBookTheme

@Composable
fun HomeScreen(
    uiState: HomeState,
    onEvent: (HomeEvent) -> Unit,
    uiEvent: Flow<HomeViewModel.UIEvent>
) {


    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }
    TopBar(
        title = stringResource(id = R.string.main)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {}) {
                Text(text = "Empty")
            }
        }
    }
}

@Preview
@Composable
fun showHomeScreen() {
    ReciepeBookTheme {
        HomeScreen(uiState = HomeState(), onEvent = {}, uiEvent = flow {})
    }
}