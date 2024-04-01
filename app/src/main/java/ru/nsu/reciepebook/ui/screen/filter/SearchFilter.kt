package ru.nsu.reciepebook.ui.screen.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.flow.Flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.TopBarWithArrow

@Composable
fun SearchFilter(
    uiState: SearchFilterState,
    onEvent: (SearchFilterEvent) -> Unit,
    uiEvent: Flow<SearchFilterViewModel.UIEvent>,
    navigateUp: () -> Unit,
    onDone: (Array<String>) -> Unit
) {
    var counter by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }
    TopBarWithArrow(
        title = stringResource(id = R.string.filter),
        onBackArrow = navigateUp
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = counter.toString(),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Green
            )
            Button(onClick = { counter++ }) {
                Text(text = "Increment")
            }
            Button(onClick = { onDone(arrayOf("tag1", "tag2", "tag3")) }) {
                Text(text = "DONE")
            }
        }
    }
}