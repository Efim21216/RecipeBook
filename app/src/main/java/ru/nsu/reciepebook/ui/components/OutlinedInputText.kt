package ru.nsu.reciepebook.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.nsu.reciepebook.ui.theme.Primary200
import ru.nsu.reciepebook.ui.theme.Primary500

@Composable
fun OutlinedInputText(
    modifier: Modifier = Modifier,
    value: String,
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
            Text(text = hint ?: "Hello")
        },
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedBorderColor = Primary200,
            unfocusedBorderColor = Primary200
        ),
        singleLine = isSingleLine,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun OutlinedButton2(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
    elevation: ButtonElevation? = null,
    border: BorderStroke? = ButtonDefaults.outlinedButtonBorder,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) =
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content
    )
class SuffixTransformation(val suffix: String) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {

        val result = text + AnnotatedString(suffix)

        val textWithSuffixMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (text.isEmpty()) return 0
                if (offset >=  text.length) return text.length;                    return offset
            }
        }

        return TransformedText(result, textWithSuffixMapping )
    }
}
@Composable
fun OutlinedInputLongKK(
    modifier: Modifier = Modifier,
    value: Long,
    label: String? = null,
    hint: String? = null,
    onValueChange: (Long) -> Unit,
    isSingleLine: Boolean = true,
    suffixText: String = "ккал"
) {
    val textValue = if (value == 0L) "" else "$value"

    OutlinedTextField(
        modifier = modifier,
        value = textValue,
        onValueChange = { newText ->
            newText.toLongOrNull()?.let { onValueChange(it) }
        },
        placeholder = { Text(text = hint ?: "") },
        label = { Text(text = label ?: "") },
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedBorderColor = Primary200,
            unfocusedBorderColor = Primary200
        ),
        singleLine = isSingleLine,
        visualTransformation = if (value != 0L) SuffixTransformation(suffixText) else VisualTransformation.None

    )

}