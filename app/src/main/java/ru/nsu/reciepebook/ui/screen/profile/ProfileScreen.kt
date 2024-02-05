package ru.nsu.reciepebook.ui.screen.profile

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
import kotlinx.coroutines.flow.Flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.TopBar

@Composable
fun ProfileScreen(
    uiState: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    uiEvent: Flow<ProfileViewModel.UIEvent>,
    toMyRecipes: () -> Unit,
    toAddRecipeInfo: () -> Unit,
) {

    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }

    TopBar(
        title = stringResource(id = R.string.profile)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = toMyRecipes) {
                Text(text = "To my recipes")
            }
            Button(onClick = toAddRecipeInfo) {
                Text(text = "To add recipe")
            }
        }
    }
}