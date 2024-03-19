package ru.nsu.reciepebook.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun InputText(
    modifier: Modifier = Modifier,
    value: String,
    label: String? = null,
    hint: String? = null,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = {onValueChange(it)},
        placeholder = {
            Text(text = hint ?: "")
        },
        label = {
            Text(text = label ?: "")
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background
        ),

        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}