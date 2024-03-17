package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.screen.add_recipe.AddRecipeViewModel
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.SideBar
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Black75
import ru.nsu.reciepebook.ui.theme.Green200
import ru.nsu.reciepebook.ui.theme.Typography

@Composable
fun AddRecipeIngredients(
    uiState: AddRecipeIngredientsState,
    onEvent: (AddRecipeIngredientsEvent) -> Unit,
    uiEvent: Flow<AddRecipeViewModel.UIEventIngredients>,
    navigateUp: () -> Unit,
    toAddStep: () -> Unit
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
        SideBar(uiState.numberOfSteps, -1)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(uiState.ingredients) { ingredient ->
                    IngredientItem(
                        ingredient = ingredient,
                        onDelete = {/* удаление элемента  */},
                        onEdit = {/* редактирование элемента  */} )
                }

            }
            Spacer(modifier = Modifier.height(2.dp))
            Button(
                onClick = {

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
                    text = stringResource(id = R.string.next_stage),
                    style = Typography.bodyLarge,
                    color = Black500
                )
            }

        }}
    }
}

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    onDelete: (Ingredient) -> Unit,
    onEdit: (Ingredient) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Black75),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row() {
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = ingredient.name, style = Typography.bodyLarge)
        }
        Text(text = "${ingredient.quantity} ${ingredient.quantityType}",
            style = Typography.bodyLarge)
        Row() {
            IconButton(onClick = {onEdit(ingredient) }) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = {onDelete(ingredient) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewAddRecipeIngredients() {
    AddRecipeIngredients(
        uiState = AddRecipeIngredientsState(
            ingredients = listOf(
                Ingredient("Томаты", "100", "кг"),
                Ingredient("Томаты", "100", "кг"),
                Ingredient("Томаты", "100", "кг"),
                Ingredient("Томаты", "100", "кг"),
                Ingredient("Томаты", "100", "кг"),
                Ingredient("Томаты", "100", "кг"),
                Ingredient("Огурцы", "50", "г")),
            numberOfSteps = 4,
        ),
        onEvent = {  },
        uiEvent = flowOf(),
        navigateUp = {  },
        toAddStep = {  }
    )
}