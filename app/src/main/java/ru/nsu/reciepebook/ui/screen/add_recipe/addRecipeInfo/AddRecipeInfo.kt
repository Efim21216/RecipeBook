package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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


@OptIn(ExperimentalMaterial3Api::class)
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
                    style = Typography.headlineMedium
                )

                OutlinedInputText(
                    modifier = Modifier.height(140.dp),
                    value = uiState.description,
                    isSingleLine = false,
                    onValueChange = {
                        onEvent(AddRecipeInfoEvent.OnChangeDescription(it))
                    })
                Button(onClick = { timerState.show() }) {
                    Text(text = stringResource(id = R.string.enter_time))
                }
                if (uiState.timeInSeconds != 0L) {
                    Text(text = convertLongToTime(uiState.timeInSeconds))
                }

            }
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