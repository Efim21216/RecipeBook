package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Typography


@Composable
fun ChooseComplexityAndType(
    uiState: AddRecipeInfoState,
    onEvent: (AddRecipeInfoEvent) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.complexity),
                style = Typography.headlineMedium
            )
            Spacer(Modifier.height(10.dp))
            var expandedComplexity by remember {
                mutableStateOf(false)
            }
            DropdownSelector(
                expanded = expandedComplexity,
                items = uiState.itemsComplexity,
                selectedIndex = uiState.selectedIndexComplexity,
                onOpen = { expandedComplexity = true },
                onDismiss = { expandedComplexity = false },
                onItemClick = {
                    expandedComplexity = false
                    onEvent(AddRecipeInfoEvent.OnChangeComplexity(it))
                },
                modifier = Modifier
                    .width(120.dp)
                    .height(45.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Black500
                )
            )
        }
        Column {
            Text(
                text = stringResource(id = R.string.type_of_dish),
                style = Typography.headlineMedium
            )
            Spacer(Modifier.height(10.dp))
            var expandedType by remember {
                mutableStateOf(false)
            }
            DropdownSelector(
                expanded = expandedType,
                items = uiState.itemsType,
                selectedIndex = uiState.selectedIndexType,
                onOpen = { expandedType = true },
                onDismiss = { expandedType = false },
                onItemClick = {
                    expandedType = false
                    onEvent(AddRecipeInfoEvent.OnChangeType(it))
                },
                modifier = Modifier
                    .width(120.dp)
                    .height(45.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Black500
                )
            )
        }
    }
}
