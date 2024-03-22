package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.nsu.reciepebook.ui.theme.Typography


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TagsInputField(
    inputText: String,
    suggestedTags: List<String>,
    modifier: Modifier,
    colors: TextFieldColors,
    addTag: (String) -> Unit,
    onChange: (String) -> Unit,
    clearInput: () -> Unit,
) {
    val controller = LocalSoftwareKeyboardController.current
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val isShowKeyboard by rememberUpdatedState(WindowInsets.isImeVisible)

    LaunchedEffect(WindowInsets.isImeVisible) {
        isExpanded = isShowKeyboard && isExpanded
    }
    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        expanded = isExpanded && isShowKeyboard,
        onExpandedChange = {
            isExpanded = !isExpanded
        }) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { onChange(it) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            singleLine = true,
            placeholder = { Text("Введите тег...") },
            colors = colors,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            trailingIcon = {
                if (inputText.isNotEmpty()) {
                    IconButton(onClick = clearInput) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Очистить")
                    }
                }
            }
        )
        ExposedDropdownMenu(
            expanded = isExpanded && isShowKeyboard,
            onDismissRequest = { isExpanded = false }) {

            suggestedTags.forEach {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it,
                            style = Typography.bodyLarge
                        )
                    },
                    onClick = {
                        addTag(it)
                        controller?.hide()
                        isExpanded = false
                    })
            }
        }
    }
}
