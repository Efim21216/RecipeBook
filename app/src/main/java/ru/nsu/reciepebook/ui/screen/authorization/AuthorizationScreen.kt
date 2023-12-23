package ru.nsu.reciepebook.ui.screen.authorization

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.InputText
import ru.nsu.reciepebook.util.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: AuthorizationViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }

        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.authorization),
                    style = MaterialTheme.typography.headlineLarge
                )
            })
        }
    ) {paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier
                .padding(PaddingValues(top = 45.dp)))
            InputText(
                modifier = Modifier
                    .padding(PaddingValues(40.dp, 0.dp))
                    .fillMaxWidth(),
                value = viewModel.email.value.text,
                label = stringResource(id = R.string.email),
                hint = viewModel.email.value.hint,
                onValueChange = {viewModel.onEvent(AuthorizationEvent.OnChangeEmail(it))}
            )
            Spacer(modifier = Modifier
                .padding(PaddingValues(top = 40.dp)))
            InputText(
                modifier = Modifier
                    .padding(PaddingValues(40.dp, 0.dp))
                    .fillMaxWidth(),
                value = viewModel.password.value.text,
                label = stringResource(id = R.string.password),
                hint = viewModel.password.value.hint,
                onValueChange = {viewModel.onEvent(AuthorizationEvent.OnChangePassword(it))},
                isPassword = true
            )
            Spacer(modifier = Modifier
                .padding(PaddingValues(top = 100.dp)))
            Button(
                onClick = {
                    viewModel.onEvent(AuthorizationEvent.Authorize)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.entry),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Spacer(modifier = Modifier
                .padding(PaddingValues(top = 10.dp)))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.not_yet_reg),
                    style = MaterialTheme.typography.headlineSmall,
                )
                TextButton(
                    onClick = { viewModel.onEvent(AuthorizationEvent.ToReg) }
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.registration),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }
        }
    }
}
