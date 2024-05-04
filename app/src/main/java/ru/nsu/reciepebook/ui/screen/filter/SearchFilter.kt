    package ru.nsu.reciepebook.ui.screen.filter

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.OutlinedInputText
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.AddRecipeInfoEvent
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.ChooseComplexityAndType
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.ChooseTimeAndKcal
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.TagChip
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.TagsInputField
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.createTmpFile
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.Ingredient
import ru.nsu.reciepebook.ui.screen.cooking.CookingInfoState
import ru.nsu.reciepebook.ui.screen.recipeInfo.RecipeInfoViewModel
import ru.nsu.reciepebook.ui.theme.Primary200
import ru.nsu.reciepebook.ui.theme.Typography
    @Composable
fun SearchFilter(
    uiState: SearchFilterState,
    onEvent: (SearchFilterEvent) -> Unit,
    uiEvent: Flow<SearchFilterViewModel.UIEvent>,
    navigateUp: () -> Unit,
    onDone: (Array<String>) -> Unit
) {
    var counter by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }
    TopBarWithArrow(
        title = stringResource(id = R.string.filter),
        onBackArrow = navigateUp
    ) { padding ->
        Column(
            modifier = Modifier.imePadding()
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.specify_tags),
                style = Typography.headlineMedium
            )
            Spacer(Modifier.height(12.dp))
            TagsInputField(
                inputText = uiState.tagInput,
                suggestedTags = uiState.suggestedTags,
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth(),
                onChange = {
                   // onEvent(AddRecipeInfoEvent.OnChangeInputTag(it))
                },
                addTag = {
                   // onEvent(AddRecipeInfoEvent.OnAddTag(it))
                },
                clearInput = {
                   // onEvent(AddRecipeInfoEvent.OnClearTag)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedBorderColor = Primary200,
                    unfocusedBorderColor = Primary200
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column {
                uiState.displayedTags.forEach { tag ->
                    TagChip(
                        tag = tag,
                        onRemove = {
                           // onEvent(AddRecipeInfoEvent.OnRemoveTag(tag))
                        }
                    )
                }
            }
            val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
            ) { uri ->
                /*if (uri == null)
                    return@rememberLauncherForActivityResult
                onEvent(AddRecipeInfoEvent.OnImageChange(createTmpFile(context, uri, "info")))*/
            }
        }
    }
}
    @Preview(showBackground = true)
    @Composable
    fun SearchFilterTest() {
        val li: List<String> = listOf("#быстро")
        SearchFilter(
            uiState =  SearchFilterState(displayedTags = li, suggestedTags = emptyList()),
            onEvent={},
        uiEvent= emptyFlow<SearchFilterViewModel.UIEvent>(),
        navigateUp= {  },
        onDone= {  }

        )
    }