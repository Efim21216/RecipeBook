package ru.nsu.reciepebook.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.RecipeCard
import ru.nsu.reciepebook.ui.components.TopBar
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Primary300
import ru.nsu.reciepebook.ui.theme.ReciepeBookTheme
import ru.nsu.reciepebook.ui.theme.Typography


@Composable
fun HomeScreen(
    uiState: HomeState,
    onEvent: (HomeEvent) -> Unit,
    uiEvent: Flow<HomeViewModel.UIEvent>,
    toAddRecipe: () -> Unit,
    toFavorite: () -> Unit,
    toMyRecipes: () -> Unit,
) {


    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }
    TopBar(
        title = stringResource(id = R.string.quick_actions)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
            ) {
                QuickAction(onClick = toAddRecipe, text = stringResource(id = R.string.add_recipe))
                QuickAction(onClick = toFavorite, text = stringResource(id = R.string.favorite))
                QuickAction(onClick = toMyRecipes, text = stringResource(id = R.string.my_recipes))
            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                modifier = Modifier.padding(15.dp, 0.dp),
                text = stringResource(id = R.string.new_recipes),
                color = Black500,
                style = Typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn {
                items(uiState.recipes) {
                    RecipeCard(shortRecipeInfo = it)
                }
            }
        }
    }
}


@Composable
fun QuickAction(
    onClick: () -> Unit,
    text: String
) {
    OutlinedButton(
        modifier = Modifier.height(55.dp),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Primary300)
    ) {
        val lines = text.split(' ')
        Column {
            lines.map {
                Text(
                    text = it,
                    color = Black500,
                    style = Typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun showHomeScreen() {
    ReciepeBookTheme {
        HomeScreen(uiState = HomeState(listOf(ShortRecipeInfo(description =
        "Описание рецепта. Первый шаг, второй шаг, третий шаг, четвёртый шаг, пятый шаг, шестой шаг, седьмой шаг"),
            ShortRecipeInfo(title = "Очень длинное название рецепта"))), onEvent = {}, uiEvent = flow {},
            toMyRecipes = {}, toAddRecipe = {}, toFavorite = {})
    }
}
