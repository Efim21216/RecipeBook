package ru.nsu.reciepebook.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.nsu.reciepebook.ui.theme.Green300
import ru.nsu.reciepebook.ui.theme.ReciepeBookTheme
import ru.nsu.reciepebook.ui.theme.Typography

@Composable
fun FilledButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Green300),
        onClick = onClick
    ) {
        Text(text = text, style = Typography.headlineMedium)
    }
}
@Preview
@Composable
fun showButton() {
    ReciepeBookTheme {
        FilledButton(onClick = { /*TODO*/ }, text = "Найти")
    }
}