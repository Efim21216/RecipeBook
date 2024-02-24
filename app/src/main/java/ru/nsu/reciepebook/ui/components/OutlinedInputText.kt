package ru.nsu.reciepebook.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.nsu.reciepebook.ui.theme.Primary500

@Composable
fun OutlinedInputText(
    modifier: Modifier = Modifier,
    value: String,
    label: String? = null,
    hint: String? = null,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    isSingleLine: Boolean = true
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = {onValueChange(it)},
        placeholder = {
            Text(text = hint ?: "")
        },
        label = {
            Text(text = label ?: "")
        },
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedBorderColor = Primary500,
            unfocusedBorderColor = Primary500
        ),
        singleLine = isSingleLine,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}