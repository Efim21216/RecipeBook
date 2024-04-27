import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.SideBar
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.Ingredient
import ru.nsu.reciepebook.ui.screen.cooking.CookingInfoState
import ru.nsu.reciepebook.ui.screen.recipeInfo.RecipeInfo
import ru.nsu.reciepebook.ui.screen.recipeInfo.RecipeInfoEvent
import ru.nsu.reciepebook.ui.screen.recipeInfo.RecipeInfoState
import ru.nsu.reciepebook.ui.screen.recipeInfo.RecipeInfoViewModel
import ru.nsu.reciepebook.ui.theme.Black50
import ru.nsu.reciepebook.ui.theme.Green200
import ru.nsu.reciepebook.ui.theme.Typography
import ru.nsu.reciepebook.ui.theme.White
import ru.nsu.reciepebook.util.Constants

@Composable
fun CookingInfo(
    uiState: CookingInfoState,
   // onEvent: (CookingInfoState) -> Unit,
    uiEvent: Flow<RecipeInfoViewModel.UIEvent>,
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
        Row(
            modifier = Modifier
                .padding(padding)
        ) {
            SideBar(uiState.numberOfSteps, -1, toAddInfo = {  })
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center
            ) {
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
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(id = R.string.description),
                    style = Typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = uiState.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(10.dp, 0.dp),
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.ingredients),
                    style = Typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(modifier = Modifier
                    .background(Black50,  shape = RoundedCornerShape(18.dp) )
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
                        text = stringResource(id = R.string.next),
                        style = Typography.bodyLarge,
                        color = White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CookingInfoPrew() {
    val sampleUiState = CookingInfoState(name = "гречка по-русски",
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
        ))
    val sampleUiEvent = emptyFlow<RecipeInfoViewModel.UIEvent>()
    val sampleNavigateUp = { }

    CookingInfo(
        uiState = sampleUiState,
      //  onEvent = sampleOnEvent,
        uiEvent = sampleUiEvent,
        navigateUp = sampleNavigateUp
    )
}