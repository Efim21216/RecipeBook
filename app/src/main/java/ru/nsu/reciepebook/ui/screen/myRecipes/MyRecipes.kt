package ru.nsu.reciepebook.ui.screen.myRecipes

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.FilledButton
import ru.nsu.reciepebook.ui.components.OutlinedInputText
import ru.nsu.reciepebook.ui.components.RecipeCard
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.screen.home.ShortRecipeInfo
import ru.nsu.reciepebook.ui.theme.ReciepeBookTheme

@Composable
fun MyRecipes(
    uiState: MyRecipesState,
    onEvent: (MyRecipesEvent) -> Unit,
    uiEvent: Flow<MyRecipesViewModel.UIEvent>,
    navigateUp: () -> Unit,
    toRecipeInfo: (Int) -> Unit,
    toFilter: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }

    TopBarWithArrow(
        title = stringResource(id = R.string.my_recipes),
        onBackArrow = navigateUp
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedInputText(
                    value = uiState.name,
                    onValueChange = {
                        onEvent(MyRecipesEvent.OnChangeName(it))
                    },
                    hint = stringResource(id = R.string.enter_name),
                    modifier = Modifier.height(55.dp)
                )
                Icon(
                    modifier = Modifier
                        .height(40.dp)
                        .width(35.dp)
                        .clickable {
                            toFilter()
                        },
                    painter = painterResource(id = R.drawable.icon_settings),
                    contentDescription = "Info"
                )
            }
            FilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 30.dp, 20.dp, 0.dp),
                onClick = { /*TODO*/ },
                text = stringResource(id = R.string.find)
            )
            Spacer(modifier = Modifier.height(30.dp))
            LazyColumn {
                items(uiState.recipes) {
                    RecipeCard(
                        shortRecipeInfo = it,
                        toRecipeInfo = { toRecipeInfo(it.id) },
                        token = uiState.token)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMyRecipes() {
    ReciepeBookTheme {
        MyRecipes(uiState = MyRecipesState(recipes = listOf(
            ShortRecipeInfo(description =
        "Описание рецепта. Первый шаг, второй шаг, третий шаг, четвёртый шаг, пятый шаг, шестой шаг, седьмой шаг"),
            ShortRecipeInfo(title = "Очень длинное название рецепта")
        )), onEvent = {}, uiEvent = flow {},
            navigateUp = { }, toRecipeInfo = { }, toFilter = {})
    }
}