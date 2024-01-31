package ru.nsu.reciepebook.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.nsu.reciepebook.R

@Composable
fun InputFields(onChangeEmail: (String) -> Unit, email: String,
                onChangePassword: (String) -> Unit, password: String) {
    Column {
        InputText(
            modifier = Modifier
                .padding(PaddingValues(40.dp, 0.dp))
                .fillMaxWidth(),
            value = email,
            label = stringResource(id = R.string.email),
            hint = stringResource(id = R.string.enter_email),
            onValueChange = onChangeEmail
        )
        Spacer(modifier = Modifier
            .padding(PaddingValues(top = 40.dp)))
        InputText(
            modifier = Modifier
                .padding(PaddingValues(40.dp, 0.dp))
                .fillMaxWidth(),
            value = password,
            label = stringResource(id = R.string.password),
            hint = stringResource(id = R.string.enter_password),
            onValueChange = onChangePassword,
            isPassword = true
        )
    }
}