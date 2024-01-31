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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.InputFields
import ru.nsu.reciepebook.ui.components.LocalSnackbarHostState
import ru.nsu.reciepebook.ui.components.TopBar
import ru.nsu.reciepebook.util.UiEvent

@Composable
fun RegistrationScreen(
    navController: NavHostController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val snackBarHostState = LocalSnackbarHostState.current
    LaunchedEffect(key1 = true) {
        /*val routes = navController.currentBackStack.value
            .map { it.destination.route }
            .joinToString(", ")
        Log.d("MyTag", "BackStack: $routes")*/
        viewModel.uiEvent.collect {event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is UiEvent.Navigate -> navController.navigate(event.route)
                is UiEvent.PopUpTo -> navController.navigate(event.route) {
                    popUpTo(event.from) {
                        inclusive = true
                    }
                }
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
            Spacer(modifier = Modifier
                .padding(PaddingValues(top = 45.dp)))
            InputFields(onChangeEmail = {viewModel.onEvent(RegistrationEvent.OnChangeEmail(it))},
                onChangePassword = {viewModel.onEvent(RegistrationEvent.OnChangePassword(it))},
                email = viewModel.email.value.text, password = viewModel.password.value.text)
            Spacer(modifier = Modifier
                .padding(PaddingValues(top = 100.dp)))
            Button(
                onClick = {
                    viewModel.onEvent(RegistrationEvent.Register)
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
            Spacer(modifier = Modifier
                .padding(PaddingValues(top = 10.dp)))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.already_have_acc),
                    style = MaterialTheme.typography.headlineSmall,
                )
                TextButton(
                    onClick = { viewModel.onEvent(RegistrationEvent.ToAuth) }
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

