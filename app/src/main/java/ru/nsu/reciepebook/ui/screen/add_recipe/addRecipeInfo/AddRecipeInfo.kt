package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
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
import ru.nsu.reciepebook.ui.components.OutlinedButton2
import ru.nsu.reciepebook.ui.components.OutlinedInputLongKK
import ru.nsu.reciepebook.ui.components.OutlinedInputText
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.components.convertLongToTime
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
import ru.nsu.reciepebook.ui.theme.Green100
import ru.nsu.reciepebook.ui.theme.Green200
import ru.nsu.reciepebook.ui.theme.Primary200

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
            SideBar(3, 2)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(20.dp, 10.dp, 0.dp, 0.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Text(
                    text = stringResource(id = R.string.name),
                    style = Typography.headlineMedium
                )
                OutlinedInputText(
                    modifier = Modifier.height(60.dp),
                    value = uiState.name,
                    onValueChange = {
                        onEvent(AddRecipeInfoEvent.OnChangeName(it))
                    })
                Spacer(Modifier.height(30.dp))
                Text(
                    text = stringResource(id = R.string.description),
                    style = Typography.headlineMedium,
                )

                OutlinedInputText(
                    modifier = Modifier.height(140.dp),
                    value = uiState.description,
                    isSingleLine = false,
                    onValueChange = {
                        onEvent(AddRecipeInfoEvent.OnChangeDescription(it))
                    })
                Spacer(Modifier.height(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.complexity),
                        style = Typography.headlineMedium
                    )
                    Text(
                        modifier = Modifier.padding(start = 87.dp),
                        text = stringResource(id = R.string.time),
                        style = Typography.headlineMedium
                    )
                }
                Spacer(Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DropdownSelector(
                        uiState.expandedComplexity, uiState.itemsComplexity,
                        uiState.selectedIndexComplexity,
                        modifier = Modifier
                            .width(100.dp)
                            .height(33.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Black500
                        )
                        )
                    OutlinedButton2(
                        //.padding(start = 87.dp)
                        onClick = { timerState.show() },
                        modifier = Modifier
                            .width(180.dp)
                            .height(33.dp)
                            .padding(start = 80.dp),
                        border = BorderStroke(1.dp, Primary200),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Black500
                        )
                    ) {
                        if (uiState.timeInSeconds != 0L) {
                            Text(
                                modifier = Modifier.padding(start = 87.dp),
                                text = convertLongToTime(uiState.timeInSeconds)
                            )
                        }
                    }
                }
                Spacer(Modifier.height(30.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.type_of_dish),
                        style = Typography.headlineMedium
                    )
                    Text(
                        modifier = Modifier.padding(start = 87.dp),
                        text = stringResource(id = R.string.calorie_content),
                        style = Typography.headlineMedium
                    )
                }
                Spacer(Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DropdownSelector(
                        uiState.expandedType, uiState.itemsType,
                        uiState.selectedIndexType,
                        modifier = Modifier
                            .width(100.dp)
                            .height(33.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Black500
                        )
                    )
                    OutlinedInputLongKK(
                        modifier = Modifier
                            .height(20.dp)
                            .width(180.dp)
                            .padding(start = 80.dp),
                        value = uiState.kcal,
                        isSingleLine = false,
                        onValueChange = {
                            onEvent(AddRecipeInfoEvent.OnChangeKcal(it))
                        })
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.specify_tags),
                    style = Typography.headlineMedium
                )
                Spacer(Modifier.height(12.dp))
                TagsInputField(uiState.inputText, uiState.tags,
                    modifier = Modifier.padding(0.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedBorderColor = Primary200,
                        unfocusedBorderColor = Primary200
                    ))
                if (uiState.timeInSeconds != 0L) {
                    Text(text = convertLongToTime(uiState.timeInSeconds))
                }
                Spacer(modifier = Modifier.height(20.dp))
                PhotoPickerButtons(singlePhotoPickerLauncher)
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { /* переход на следующий этап */ },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green200
                    )
                ) {
                    Text(text = stringResource(id = R.string.move_to_next_step),
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
            Icon(painter = painterResource(id = R.drawable.add_photo_svgrepo_com_1),
                contentDescription = "Добавить фото",
                tint = Black500)
            Text(text = stringResource(id = R.string.add_photo),
                style = Typography.headlineMedium,
                color = Black500
            )
        }
    }
}

@Composable
fun SideBar(countSteps: Int, currentStep: Int) {
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
            modifier = Modifier.clickable { })
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
    colors : ButtonColors
){
    var expanded = expanded;
    val items = items;
    var selectedIndex = selectedIndex;

    OutlinedButton2(
        //.padding(start = 87.dp)
        onClick = { expanded = true },
        modifier = modifier,
        colors = colors,
        border = BorderStroke(1.dp, Primary200),
        contentPadding = PaddingValues(
            start = 10.dp, top = 4.dp, end = 4.dp, bottom = 4.dp
        )

    ) {
        Icon(painter = painterResource(id = R.drawable.arrow_ios_down),
            contentDescription = "Info")
        Text(
            text = items[selectedIndex],
            style = Typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()

        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        items.forEachIndexed { index, text ->
            DropdownMenuItem(
                text = { Text(text) },
                onClick = {
                    selectedIndex = index
                    expanded = false
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
                    tint = Black500)
            }
        }
    }
}
@Composable
fun TagsInputField(
    inputText: String,
    tags: List<String>,
    modifier: Modifier,
    colors: TextFieldColors
) {
    var inputText = inputText;
    var tags = tags;
    // Функция для добавления нового тега
    fun addTag(tag: String) {
        if (tag.isNotEmpty() && !tags.contains(tag)) {
            tags = tags + tag
            inputText = ""
        }
    }

    // Функция для удаления существующего тега
    fun removeTag(tag: String) {
        tags = tags - tag
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            singleLine = true,
            placeholder = { Text("Введите тег...") },
            modifier = modifier,
            colors = colors,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                addTag(inputText.trim())
            }),
            trailingIcon = {
                if (inputText.isNotEmpty()) {
                    IconButton(onClick = { inputText = "" }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Очистить")
                    }
                }
            }
        )

        Column {
            tags.forEach { tag ->
                TagChip(
                    tag = tag,
                    onRemove = { removeTag(tag) }
                )
            }
        }
    }
}
@Preview
@Composable
fun PreviewAddRecipe() {
    AddRecipeInfo(
        uiState = AddRecipeInfoState("", ""),
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