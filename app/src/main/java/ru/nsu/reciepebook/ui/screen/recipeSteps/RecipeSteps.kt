package ru.nsu.reciepebook.ui.screen.recipeSteps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.flow.Flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.TopBarWithArrow

@Composable
fun RecipeSteps(
    uiState: RecipeStepsState,
    onEvent: (RecipeStepsEvent) -> Unit,
    uiEvent: Flow<RecipeStepsViewModel.UIEvent>,
    navigateUp: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }

    TopBarWithArrow(
        title = stringResource(id = R.string.step),
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
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Green
            )
        }
    }
}