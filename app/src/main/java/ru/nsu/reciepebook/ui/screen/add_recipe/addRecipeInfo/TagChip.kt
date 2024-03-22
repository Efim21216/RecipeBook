package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Green100


@Composable
fun TagChip(
    tag: String,
    onRemove: () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 4.dp),
        color = Green100,
        shape = RoundedCornerShape(16.dp),

        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .height(33.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tag,
                modifier = Modifier
                    .padding(start = 8.dp),
                color = Black500
            )
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Удалить тег",
                    tint = Black500
                )
            }
        }
    }
}
