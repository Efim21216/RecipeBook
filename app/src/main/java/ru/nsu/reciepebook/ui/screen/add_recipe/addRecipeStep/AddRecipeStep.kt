package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep

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
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.ChooseComplexityAndType
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.ChooseTimeAndKcal
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.PhotoPickerButtons
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.TagChip
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.TagsInputField
import ru.nsu.reciepebook.ui.theme.Green200
import ru.nsu.reciepebook.ui.theme.Primary200

@SuppressLint("ResourceType")
@Composable
fun AddRecipeStep(
    uiState: AddRecipeStepState,
    onEvent: (AddRecipeStepEvent) -> Unit,
    uiEvent: Flow<AddRecipeViewModel.UIEventStep>,
    navigateUp: () -> Unit,
    toRecipeInfo: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }

    TopBarWithArrow(
        title = stringResource(id = R.string.step_N) + "${uiState.currentStep + 1}",
        onBackArrow = navigateUp
    ) { padding ->
        Row(
            modifier = Modifier
                .padding(padding)
        ) {

            SideBar(uiState.countSteps, uiState.currentStep)
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
                        onEvent(AddRecipeStepEvent.OnChangeName(it))
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
                        onEvent(AddRecipeStepEvent.OnChangeDescription(it))
                    })
                Spacer(Modifier.height(20.dp))
                ChooseTimeAndKcal2(uiState = uiState, onEvent = onEvent)
                val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia(),
                ) { uri ->
                    onEvent(AddRecipeStepEvent.OnImageChange(uri))
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
                        text = stringResource(id = R.string.go_to_next_step),
                        style = Typography.bodyLarge,
                        color = Black500
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
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
                        text = stringResource(id = R.string.complete),
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
    AddRecipeStep(
        uiState = AddRecipeStepState("", "", timeInSeconds = 1000),
        onEvent = {},
        uiEvent = flow {},
        navigateUp = {},
        toRecipeInfo = {})
}

