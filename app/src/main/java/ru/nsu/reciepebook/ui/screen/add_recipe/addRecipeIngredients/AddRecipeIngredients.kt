package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients

import CustomTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.SideBar
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.screen.add_recipe.AddRecipeViewModel
import ru.nsu.reciepebook.ui.theme.Black50
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Black75
import ru.nsu.reciepebook.ui.theme.Green200
import ru.nsu.reciepebook.ui.theme.Typography
import ru.nsu.reciepebook.util.Constants

@Composable
fun AddRecipeIngredients(
    uiState: AddRecipeIngredientsState,
    onEvent: (AddRecipeIngredientsEvent) -> Unit,
    uiEvent: Flow<AddRecipeViewModel.UIEventIngredients>,
    navigateUp: () -> Unit,
    toAddStep: () -> Unit,
    toAddInfo: () -> Unit,
) {
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }

    TopBarWithArrow(
        title = stringResource(id = R.string.ingredients),
        onBackArrow = navigateUp
    ) { padding ->
        Row(
            modifier = Modifier
                .padding(padding)
        ) {
            SideBar(uiState.numberOfSteps, -1, toAddInfo = toAddInfo)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, top = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    itemsIndexed(uiState.ingredients) { idx, ingredient ->
                        IngredientItem(
                            ingredient = ingredient,
                            onDelete = { onEvent(AddRecipeIngredientsEvent.DeleteIngredient(idx)) },
                            onEdit = {
                                onEvent(
                                    AddRecipeIngredientsEvent.IngredientChanged(
                                        it,
                                        idx
                                    )
                                )
                            }
                        )
                    }

                }
                Spacer(modifier = Modifier.height(2.dp))
                Button(
                    onClick = {
                              onEvent(AddRecipeIngredientsEvent.AddIngredient)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green200
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.add_ingredient),
                        style = Typography.bodyLarge,
                        color = Black500
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp, 10.dp, 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green200
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.next),
                        style = Typography.bodyLarge,
                        color = Black500
                    )
                }

            }
        }
    }
}

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    onDelete: (Ingredient) -> Unit,
    onEdit: (Ingredient) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Black50),
        verticalAlignment = Alignment.CenterVertically
    ) {

        CustomTextField(
            modifier = Modifier
                .width(160.dp)
                .padding(start = 10.dp),
            innerPadding = PaddingValues(7.dp, 15.dp),
            value = ingredient.name,
            onValueChange = { onEdit(ingredient.copy(name = it)) },
            textStyle = Typography.bodyLarge,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            placeholder = {
                Text(text = stringResource(id = R.string.name),
                    style = Typography.bodyLarge,
                    color = Black75)
            }
        )
        CustomTextField(
            modifier = Modifier
                .width(60.dp),
            innerPadding = PaddingValues(0.dp, 15.dp),
            value = ingredient.quantity.toString(),
            onValueChange = {
                try {
                    onEdit(ingredient.copy(quantity = it.toFloat()))
                } catch (e: NumberFormatException) {
                    onEdit(ingredient.copy(quantity = 0f))
                }
            },
            textStyle = Typography.bodyLarge,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.width(4.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            DropdownInputSelector(
                selectedIndex = ingredient.measure,
                items = Constants.Measures.entries.map {
                    when (it) {
                        Constants.Measures.GRAM -> "г"
                        Constants.Measures.KILOGRAM -> "кг"
                        Constants.Measures.MILLIGRAM -> "мл"
                        Constants.Measures.LITER -> "л"
                        Constants.Measures.MILLILITER -> "мл"
                        Constants.Measures.TEE_SPOON -> "ч. л."
                        Constants.Measures.TABLE_SPOON -> "ст. л."
                        Constants.Measures.PIECE -> "шт"
                    }
                },
                onChange = {
                    onEdit(ingredient.copy(measure = it))
                }
            )
            IconButton(
                modifier = Modifier.padding(start = 50.dp),
                onClick = { onDelete(ingredient) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownInputSelector(
    selectedIndex: Int,
    items: List<String>,
    onChange: (Int) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        CustomTextField(
            modifier = Modifier
                .width(60.dp)
                .menuAnchor(),
            innerPadding = PaddingValues(0.dp, 15.dp),
            value = items[selectedIndex],
            onValueChange = { },
            textStyle = Typography.bodyLarge,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            readOnly = true,
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        onChange(index)
                        isExpanded = false
                    },
                    text = {
                        Text(text = item)
                    }
                )
            }
        }
    }
/*

    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("Label") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier.menuAnchor(),
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    }, text = {
                        Text(text = selectionOption)
                    }
                )
            }
        }
    }*/
}

@Preview(showBackground = true)
@Composable
fun PreviewAddRecipeIngredients() {
    AddRecipeIngredients(
        uiState = AddRecipeIngredientsState(
            ingredients = listOf(
                Ingredient("Томаты", 100f, 1),
                Ingredient("Томаты", 100f, 2),
                Ingredient("Томаты", 100f, 1),
                Ingredient("Томаты", 100f, 1),
                Ingredient("Томаты", 100f, 1),
                Ingredient("Томаты", 100f, 1),
                Ingredient("Томаты", 100f, 2),
                Ingredient("Томаты", 100f, 1),
                Ingredient("Томаты", 100f, 1),
                Ingredient("Томаты", 100f, 1),

            ),
            numberOfSteps = 4,
        ),
        onEvent = { },
        uiEvent = flowOf(),
        navigateUp = { },
        toAddStep = { },
        toAddInfo = {}
    )
}