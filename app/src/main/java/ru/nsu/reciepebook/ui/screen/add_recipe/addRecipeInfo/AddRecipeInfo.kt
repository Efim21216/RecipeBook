package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import ru.nsu.reciepebook.ui.components.OutlinedInputText
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.screen.add_recipe.AddRecipeViewModel
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Typography
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import ru.nsu.reciepebook.ui.components.SideBar
import ru.nsu.reciepebook.ui.theme.Green200
import ru.nsu.reciepebook.ui.theme.Primary200

@SuppressLint("ResourceType")
@Composable
fun AddRecipeInfo(
    uiState: AddRecipeInfoState,
    onEvent: (AddRecipeInfoEvent) -> Unit,
    uiEvent: Flow<AddRecipeViewModel.UIEventInfo>,
    navigateUp: () -> Unit,
    toAddIngredients: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }

    TopBarWithArrow(
        title = stringResource(id = R.string.recipe_summary),
        onBackArrow = navigateUp
    ) { padding ->
        Row(
            modifier = Modifier
                .padding(padding)
        ) {

            SideBar(0, 0, toAddIngredients)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(20.dp, 10.dp, 20.dp, 10.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Text(
                    text = stringResource(id = R.string.name),
                    style = Typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedInputText(
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth(),
                    value = uiState.name,
                    hint = "Введите название",
                    onValueChange = {
                        onEvent(AddRecipeInfoEvent.OnChangeName(it))
                    })
                Spacer(Modifier.height(30.dp))
                Text(
                    text = stringResource(id = R.string.description),
                    style = Typography.headlineMedium,
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedInputText(
                    modifier = Modifier
                        .height(140.dp)
                        .fillMaxWidth(),
                    value = uiState.description,
                    hint = "Введите описание",
                    isSingleLine = false,
                    onValueChange = {
                        onEvent(AddRecipeInfoEvent.OnChangeDescription(it))
                    })
                Spacer(Modifier.height(20.dp))
                ChooseComplexityAndType(uiState = uiState, onEvent = onEvent)
                Spacer(Modifier.height(30.dp))
                ChooseTimeAndKcal(uiState = uiState, onEvent = onEvent)
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
                        onEvent(AddRecipeInfoEvent.OnChangeInputTag(it))
                    },
                    addTag = {
                        onEvent(AddRecipeInfoEvent.OnAddTag(it))
                    },
                    clearInput = {
                        onEvent(AddRecipeInfoEvent.OnClearTag)
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
                                onEvent(AddRecipeInfoEvent.OnRemoveTag(tag))
                            }
                        )
                    }
                }
                val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                ) { uri ->
                    onEvent(AddRecipeInfoEvent.OnImageChange(uri))
                }
                Spacer(modifier = Modifier.height(20.dp))
                PhotoPickerButtons(singlePhotoPickerLauncher)
                var modifier = Modifier
                    .fillMaxWidth()
                if (uiState.selectedImageUri != null)
                    modifier = modifier
                        .height(300.dp)
                        .padding(top = 15.dp)
                AsyncImage(
                    model = uiState.selectedImageUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { onEvent(AddRecipeInfoEvent.OnDone) },
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
                        color = Black500
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewAddRecipe() {
    AddRecipeInfo(
        uiState = AddRecipeInfoState("", "", timeInSeconds = 1000),
        onEvent = {},
        uiEvent = flow {},
        navigateUp = {}) {

    }
}

