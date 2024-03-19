package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

import CustomTextField
import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.duration.DurationDialog
import com.maxkeppeler.sheets.duration.models.DurationConfig
import com.maxkeppeler.sheets.duration.models.DurationFormat
import com.maxkeppeler.sheets.duration.models.DurationSelection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.CustomOutlinedButton
import ru.nsu.reciepebook.ui.components.OutlinedInputText
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.components.leftBorder
import ru.nsu.reciepebook.ui.components.rightBorder
import ru.nsu.reciepebook.ui.components.topBorder
import ru.nsu.reciepebook.ui.screen.add_recipe.AddRecipeViewModel
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Primary300
import ru.nsu.reciepebook.ui.theme.Primary50
import ru.nsu.reciepebook.ui.theme.Primary500
import ru.nsu.reciepebook.ui.theme.Typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import ru.nsu.reciepebook.service.pad
import ru.nsu.reciepebook.ui.theme.Green100
import ru.nsu.reciepebook.ui.theme.Green200
import ru.nsu.reciepebook.ui.theme.Primary200
import kotlin.time.Duration.Companion.seconds

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeInfo(
    uiState: AddRecipeInfoState,
    onEvent: (AddRecipeInfoEvent) -> Unit,
    uiEvent: Flow<AddRecipeViewModel.UIEventInfo>,
    navigateUp: () -> Unit,
    toAddIngredients: () -> Unit
) {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        // Обработка выбранного изображения
    }
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
            val timerState = rememberUseCaseState()
            DurationDialog(
                state = timerState,
                selection = DurationSelection { newTimeInSeconds ->
                    onEvent(AddRecipeInfoEvent.OnChangeTime(newTimeInSeconds))
                },
                config = DurationConfig(
                    timeFormat = DurationFormat.HH_MM_SS
                )
            )
            SideBar(3, 2, toAddIngredients)
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
                    isSingleLine = false,
                    onValueChange = {
                        onEvent(AddRecipeInfoEvent.OnChangeDescription(it))
                    })
                Spacer(Modifier.height(20.dp))
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

                Spacer(Modifier.height(30.dp))
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            modifier = Modifier,
                            text = stringResource(id = R.string.time),
                            style = Typography.headlineMedium
                        )
                        Spacer(Modifier.height(10.dp))
                        CustomOutlinedButton(
                            onClick = { timerState.show() },
                            modifier = Modifier
                                .width(120.dp)
                                .height(45.dp),
                            border = BorderStroke(1.dp, Primary200),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Black500
                            ),
                            contentPadding = PaddingValues(15.dp, 8.dp)
                        ) {
                            uiState.timeInSeconds.seconds.toComponents { h, m, s, _ ->
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    style = Typography.bodyLarge,
                                    text = "${h.pad()}:${m.pad()}:${s.pad()}"
                                )
                            }
                        }
                    }
                    Column {
                        Text(
                            text = stringResource(id = R.string.calorie_content),
                            style = Typography.headlineMedium
                        )
                        Spacer(Modifier.height(10.dp))
                        CustomTextField(
                            modifier = Modifier
                                .border(1.dp, Primary200, RoundedCornerShape(8.dp))
                                .width(120.dp)
                                .height(45.dp),
                            value = uiState.kcal.toString(),
                            onValueChange = {
                                val value = if (it == "") 0 else it.toLong()
                                onEvent(AddRecipeInfoEvent.OnChangeKcal(value))
                            },
                            suffix = {
                                Text(
                                    text = "ккал",
                                    style = Typography.bodyLarge
                                )
                            },
                            innerPadding = PaddingValues(15.dp, 8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.specify_tags),
                    style = Typography.headlineMedium
                )
                Spacer(Modifier.height(12.dp))
                TagsInputField(
                    inputText = uiState.tagInput,
                    tags = uiState.tags,
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth(),
                    onChange = {
                        onEvent(AddRecipeInfoEvent.OnChangeTag(it))
                    },
                    addTag = {
                        onEvent(AddRecipeInfoEvent.OnAddTag(it))
                    },
                    removeTag = {
                        onEvent(AddRecipeInfoEvent.OnRemoveTag(it))
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
                Spacer(modifier = Modifier.height(20.dp))
                PhotoPickerButtons(singlePhotoPickerLauncher)
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = toAddIngredients,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green200
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.move_to_next_step),
                        style = Typography.headlineMedium,
                        color = Black500
                    )
                }
            }
        }
    }
}

@Composable
fun PhotoPickerButtons(
    singlePhotoPickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green200
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add_photo_svgrepo_com_1),
                contentDescription = "Добавить фото",
                tint = Black500
            )
            Text(
                text = stringResource(id = R.string.add_photo),
                style = Typography.headlineMedium,
                color = Black500
            )
        }
    }
}

@Composable
fun SideBar(
    countSteps: Int,
    currentStep: Int,
    toAddIngredients: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(55.dp)
            .drawBehind {
                rightBorder(2.dp.toPx(), Primary500)
                leftBorder(2.dp.toPx(), Primary500)
                topBorder(2.dp.toPx(), Primary500)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Icon(painter = painterResource(id = R.drawable.icon_info),
            contentDescription = "Info",
            modifier = Modifier.clickable { })
        Spacer(modifier = Modifier.height(20.dp))
        Icon(painter = painterResource(id = R.drawable.icon_ingridients),
            contentDescription = "Info",
            modifier = Modifier.clickable { toAddIngredients() })
        repeat(countSteps) {
            Spacer(modifier = Modifier.height(20.dp))
            val backgroundColor: Color
            val textColor: Color
            if (it == currentStep) {
                backgroundColor = Primary300
                textColor = Color.White
            } else {
                backgroundColor = Primary50
                textColor = Black500
            }
            IconButton(
                onClick = { },
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = CircleShape
                    )
                    .border(2.dp, Primary300, shape = CircleShape)

            ) {
                Text(
                    text = "${it + 1}",
                    style = Typography.headlineMedium, color = textColor
                )
            }
        }
    }
}

@Composable
fun DropdownSelector(
    expanded: Boolean,
    items: List<String>,
    selectedIndex: Int,
    modifier: Modifier,
    colors: ButtonColors,
    onItemClick: (Int) -> Unit,
    onOpen: () -> Unit,
    onDismiss: () -> Unit,
) {


    CustomOutlinedButton(
        onClick = onOpen,
        modifier = modifier,
        colors = colors,
        border = BorderStroke(1.dp, Primary200),
        contentPadding = PaddingValues(
            start = 15.dp, end = 4.dp
        )

    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_ios_down),
            contentDescription = "Info"
        )
        Text(
            text = items[selectedIndex],
            style = Typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()

        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
    ) {
        items.forEachIndexed { index, text ->
            DropdownMenuItem(
                text = { Text(text) },
                onClick = {
                    onItemClick(index)
                }
            )
        }
    }

}

@Composable
fun TagChip(
    tag: String,
    onRemove: () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 4.dp),
        color = Green100,
        shape = RoundedCornerShape(16.dp),

        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .height(33.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tag,
                modifier = Modifier
                    .padding(start = 8.dp),
                color = Black500
            )
            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Удалить тег",
                    tint = Black500
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TagsInputField(
    inputText: String,
    tags: List<String>,
    modifier: Modifier,
    colors: TextFieldColors,
    addTag: (String) -> Unit,
    removeTag: (String) -> Unit,
    onChange: (String) -> Unit,
    clearInput: () -> Unit,
) {

    var isExpanded by remember {
        mutableStateOf(false)
    }
    val isShowKeyboard by rememberUpdatedState(WindowInsets.isImeVisible)

    LaunchedEffect(WindowInsets.isImeVisible) {
        isExpanded = isShowKeyboard && isExpanded
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "$isShowKeyboard --- $isExpanded")
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = isExpanded && isShowKeyboard,
            onExpandedChange = {
                isExpanded = !isExpanded
            }) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { onChange(it) },
                modifier = Modifier
                    .menuAnchor()
                    .onFocusChanged {
                        isExpanded = it.isFocused
                    },
                singleLine = true,
                placeholder = { Text("Введите тег...") },
                colors = colors,
                shape = RoundedCornerShape(8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    addTag(inputText.trim())
                }),
                trailingIcon = {
                    if (inputText.isNotEmpty()) {
                        IconButton(onClick = clearInput) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Очистить")
                        }
                    }
                }
            )
            ExposedDropdownMenu(expanded = isExpanded && isShowKeyboard, onDismissRequest = { isExpanded = false }) {
                listOf("1", "2", "3", "4").forEach {
                    DropdownMenuItem(text = { Text(it) }, onClick = { isExpanded = false})
                }
            }
        }

        /*Spacer(modifier = Modifier.height(10.dp))

        Column {
            tags.forEach { tag ->
                TagChip(
                    tag = tag,
                    onRemove = { removeTag(tag) }
                )
            }
        }*/
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

/*
 var selectedImageUri by remember {
            mutableStateOf<Uri?>(null)
        }
        var selectedImageUris by remember {
            mutableStateOf<List<Uri>>(emptyList())
        }
        val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> selectedImageUri = uri }
        )
        val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = { uris -> selectedImageUris = uris }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                        Text(text = "Pick one photo")
                    }
                    Button(onClick = {
                        multiplePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }) {
                        Text(text = "Pick multiple photo")
                    }
                }
            }
            item {
                Text(text = selectedImageUri?.path ?: "")
            }

            item {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }

            items(selectedImageUris) { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        }
 */