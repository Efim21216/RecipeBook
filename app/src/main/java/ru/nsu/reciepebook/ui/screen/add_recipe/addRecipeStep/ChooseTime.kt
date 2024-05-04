package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
fun ChooseTime(
    uiState: AddRecipeStepState,
    onEvent: (AddRecipeStepEvent) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        val timerState = rememberUseCaseState()
        DurationDialog(
            state = timerState,
            selection = DurationSelection { newTimeInSeconds ->
                onEvent(AddRecipeStepEvent.OnChangeTime(newTimeInSeconds.toInt()))
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
            Spacer(Modifier.height(15.dp))
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
                uiState.steps[uiState.currentStep].timeInSeconds.seconds.toComponents { h, m, s, _ ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = Typography.bodyLarge,
                        text = "${h.pad()}:${m.pad()}:${s.pad()}"
                    )
                }
            }
        }

    }
}