package ru.nsu.reciepebook.ui.screen.authorization

import android.util.Log
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
import ru.nsu.reciepebook.util.UiEvent


@Composable
fun AuthorizationScreen(
    navController: NavHostController,
    viewModel: AuthorizationViewModel = hiltViewModel()
) {
    val snackBarHostState = LocalSnackbarHostState.current

    LaunchedEffect(key1 = true) {
        navController.addOnDestinationChangedListener { controller, _, _ ->
            val routes = controller.currentBackStack.value
                .map { it.destination.route }
                .joinToString(", ")
            Log.d("MyTag", "BackStack: $routes")
        }

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
    Column(
        modifier = Modifier
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
        InputFields(onChangeEmail = {viewModel.onEvent(AuthorizationEvent.OnChangeEmail(it))},
            onChangePassword = {viewModel.onEvent(AuthorizationEvent.OnChangePassword(it))},
            email = viewModel.email.value.text, password = viewModel.password.value.text)
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

