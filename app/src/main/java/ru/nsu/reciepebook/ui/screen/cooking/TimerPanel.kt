package ru.nsu.reciepebook.ui.screen.cooking

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.service.CountdownState
import ru.nsu.reciepebook.service.ServiceHelper
import ru.nsu.reciepebook.service.TimerState
import ru.nsu.reciepebook.ui.theme.Typography
import ru.nsu.reciepebook.util.Constants

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TimerPanel(
    timerState: TimerState
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.baseline_timer_24),
                contentDescription = "timer")
            AnimatedTimer(hours = timerState.hours,
                minutes = timerState.minutes,
                seconds = timerState.seconds)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.height(60.dp)
        ) {
            OutlinedButton(
                modifier = Modifier
                    .width(130.dp)
                    .fillMaxHeight(0.8f),
                shape = RoundedCornerShape(15.dp),
                onClick = {
                    ServiceHelper.triggerCountdownService(
                        context = context,
                        action = if (timerState.state == CountdownState.Started) Constants.ACTION_SERVICE_STOP
                        else Constants.ACTION_SERVICE_START,
                        stepNumber = 42
                    )
                }
            ) {
                Text(
                    text = if (timerState.state == CountdownState.Started) "Стоп"
                    else if ((timerState.state == CountdownState.Stopped)) "Рестарт"
                    else "Старт",
                    style = Typography.headlineSmall
                )
            }
            if (timerState.state != CountdownState.Idle) {
                OutlinedButton(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .width(130.dp)
                        .fillMaxHeight(0.8f),
                    shape = RoundedCornerShape(15.dp),
                    onClick = {
                        ServiceHelper.cancelCountdownService(
                            context = context
                        )
                    }
                ) {
                    Text(
                        text = "Отмена",
                        style = Typography.headlineSmall)
                }
            }

        }
    }
}