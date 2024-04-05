package ru.nsu.reciepebook.ui.screen.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.service.CountdownService
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.theme.ReciepeBookTheme

@Composable
fun Favorite(
    uiState: FavoriteState,
    onEvent: (FavoriteEvent) -> Unit,
    uiEvent: Flow<FavoriteViewModel.UIEvent>,
    navigateUp: () -> Unit,
    countdownService: CountdownService
) {

    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }

    TopBarWithArrow(
        title = stringResource(id = R.string.favorite),
        onBackArrow = navigateUp
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center
        ) {
           Text(text = "Favorite")
        }
    }
}


@Preview
@Composable
fun PreviewFavorite() {
    ReciepeBookTheme {
        Favorite(
            uiState = FavoriteState(),
            onEvent = {},
            uiEvent = flow { },
            navigateUp = {},
            countdownService = CountdownService()
        )
    }
}