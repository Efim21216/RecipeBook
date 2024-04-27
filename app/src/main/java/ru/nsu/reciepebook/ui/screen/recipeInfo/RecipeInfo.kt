package ru.nsu.reciepebook.ui.screen.recipeInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.AddRecipeInfoEvent
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.AddRecipeIngredientsState
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.Ingredient
import ru.nsu.reciepebook.ui.theme.Black50
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Green200
import ru.nsu.reciepebook.ui.theme.Typography
import ru.nsu.reciepebook.util.Constants

@Composable
fun RecipeInfo(
    uiState: RecipeInfoState,
    onEvent: (RecipeInfoEvent) -> Unit,
    uiEvent: Flow<RecipeInfoViewModel.UIEvent>,
    recipeId: Int,
    navigateUp: () -> Unit
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }

    TopBarWithArrow(
        title = uiState.name,
        onBackArrow = navigateUp
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            AsyncImage(
                model = uiState.imageUrl,
                contentDescription = "Loaded image",  // Описание для доступности
                placeholder = painterResource(id = R.drawable.image_default_preview),
                error = painterResource(id = R.drawable.image_default_preview),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(15.dp, 0.dp, 0.dp, 0.dp)
            ) {
                AsyncImage(
                    model = uiState.profileImage,
                    contentDescription = "profile",
                    placeholder = painterResource(id = R.drawable.image_profile),
                    error = painterResource(id = R.drawable.image_profile),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = uiState.author,
                    color = Black500,
                    style = Typography.headlineLarge,
                    modifier = Modifier
                        .padding(20.dp, 0.dp, 0.dp, 0.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = stringResource(id = R.string.description),
                style = Typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = uiState.description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    //
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green200
                )
            ) {
                Text(
                    text = stringResource(id = R.string.start),
                    style = Typography.bodyLarge,
                    color = Black500
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.padding(start = 25.dp),
                text = stringResource(id = R.string.favourites) +' '
                        + uiState.favourite  + ' ' + uiState.favouritesString,
                style = Typography.headlineSmall
            )
            Text(
                text = stringResource(id = R.string.ingredients),
                style = Typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(modifier = Modifier
                .background(Black50)
                .fillMaxWidth()
                .padding(16.dp)) {
                val items = Constants.Measures.entries.map {
                    when (it) {
                        Constants.Measures.GRAM -> "г"
                        Constants.Measures.KILOGRAM -> "кг"
                        Constants.Measures.MILLIGRAM -> "мг"
                        Constants.Measures.LITER -> "л"
                        Constants.Measures.MILLILITER -> "мл"
                        Constants.Measures.TEE_SPOON -> "ч. л."
                        Constants.Measures.TABLE_SPOON -> "ст. л."
                        Constants.Measures.PIECE -> "шт"
                    }
                }
                uiState.ingredients.forEach { ingredient ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                        Text(text = "${ingredient.name}", modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(end = 8.dp),
                            maxLines = 3,
                        style = Typography.headlineSmall)
                        Text(text = "${ingredient.quantity} ${items[ingredient.measure]}",
                            modifier = Modifier.wrapContentWidth(Alignment.End).align(Alignment.CenterVertically)
                        , style = Typography.headlineSmall)
                    }

                    // Если есть дополнительное примечание к ингредиенту, отобразить его
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Button(
                onClick = {
                    //
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green200
                )
            ) {
                Text(
                    text = stringResource(id = R.string.Go_steps),
                    style = Typography.bodyLarge,
                    color = Black500
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRecipeInfo() {
    val sampleUiState = RecipeInfoState(name = "гречка по-русски",
        description = "Яндекс Переводчик — синхронный перевод для 102 языков, подсказки при наборе, словарь с транскрипцией, произношением и примерами употребления слов, а также многое другое. Самые популярные направления перевода. Русский. Английский Английский. Русский Русский. Немецкий Немецкий. Русский Русский",
    ingredients = listOf(
        Ingredient("Томаты Томатыf Томаты", 100f, 1),
        Ingredient("Томаты", 100f, 2),
        Ingredient("Томаты", 100f, 1),
        Ingredient("Томаты", 100f, 1),
        Ingredient("Томаты            пра        апр", 100f, 1),
        Ingredient("Томаты", 100f, 2),
        Ingredient("Томаты", 100f, 1),
        Ingredient("Томаты", 100f, 1),
        Ingredient("Томаты", 100f, 1),
    ), author = "Nikita")
    val sampleOnEvent = { _: RecipeInfoEvent -> }
    val sampleUiEvent = emptyFlow<RecipeInfoViewModel.UIEvent>()
    val sampleRecipeId = 1
    val sampleNavigateUp = { }

    RecipeInfo(
        uiState = sampleUiState,
        onEvent = sampleOnEvent,
        uiEvent = sampleUiEvent,
        recipeId = sampleRecipeId,
        navigateUp = sampleNavigateUp
    )
}