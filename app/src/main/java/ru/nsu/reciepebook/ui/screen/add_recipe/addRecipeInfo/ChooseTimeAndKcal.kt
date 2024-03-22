package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

import CustomTextField
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.duration.DurationDialog
import com.maxkeppeler.sheets.duration.models.DurationConfig
import com.maxkeppeler.sheets.duration.models.DurationFormat
import com.maxkeppeler.sheets.duration.models.DurationSelection
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.service.pad
import ru.nsu.reciepebook.ui.components.CustomOutlinedButton
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Primary200
import ru.nsu.reciepebook.ui.theme.Typography
import kotlin.time.Duration.Companion.seconds


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseTimeAndKcal(
    uiState: AddRecipeInfoState,
    onEvent: (AddRecipeInfoEvent) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
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
}