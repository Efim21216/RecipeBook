package ru.nsu.reciepebook.ui.screen.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.InputFields
import ru.nsu.reciepebook.ui.components.LocalSnackbarHostState
import ru.nsu.reciepebook.ui.components.TopBar


@Composable
fun RegistrationScreen(
    toMain: () -> Unit,
    toAuth: () -> Unit,
    uiEvent: Flow<RegistrationViewModel.UIEvent>,
    onEvent: (RegistrationEvent) ->Unit,
    uiState: RegState
) {
    val snackBarHostState = LocalSnackbarHostState.current
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is RegistrationViewModel.UIEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }

                is RegistrationViewModel.UIEvent.ToMain -> toMain()
            }

        }
    }
    TopBar(
        title = stringResource(id = R.string.registration)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(
                modifier = Modifier
                    .padding(PaddingValues(top = 45.dp))
            )
            InputFields(
                onChangeEmail = { onEvent(RegistrationEvent.OnChangeEmail(it)) },
                onChangePassword = { onEvent(RegistrationEvent.OnChangePassword(it)) },
                email = uiState.email, password = uiState.password
            )
            Spacer(
                modifier = Modifier
                    .padding(PaddingValues(top = 100.dp))
            )
            Button(
                onClick = {
                    onEvent(RegistrationEvent.Register)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.reg),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Spacer(
                modifier = Modifier
                    .padding(PaddingValues(top = 10.dp))
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.already_have_acc),
                    style = MaterialTheme.typography.headlineSmall,
                )
                TextButton(
                    onClick = toAuth
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.entry),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }
        }
    }
}

