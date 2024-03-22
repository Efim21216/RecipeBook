package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.CustomOutlinedButton
import ru.nsu.reciepebook.ui.theme.Primary200
import ru.nsu.reciepebook.ui.theme.Typography


@Composable
fun DropdownSelector(
    expanded: Boolean,
    items: List<String>,
    selectedIndex: Int,
    modifier: Modifier,
    colors: ButtonColors,
    onItemClick: (Int) -> Unit,
    onOpen: () -> Unit,
    onDismiss: () -> Unit,
) {


    CustomOutlinedButton(
        onClick = onOpen,
        modifier = modifier,
        colors = colors,
        border = BorderStroke(1.dp, Primary200),
        contentPadding = PaddingValues(
            start = 15.dp, end = 4.dp
        )

    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_ios_down),
            contentDescription = "Info"
        )
        Text(
            text = items[selectedIndex],
            style = Typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()

        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
    ) {
        items.forEachIndexed { index, text ->
            DropdownMenuItem(
                text = { Text(text) },
                onClick = {
                    onItemClick(index)
                }
            )
        }
    }

}