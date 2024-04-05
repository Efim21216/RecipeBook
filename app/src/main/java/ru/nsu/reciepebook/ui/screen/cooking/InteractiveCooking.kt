package ru.nsu.reciepebook.ui.screen.cooking

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.service.CountdownState
import ru.nsu.reciepebook.service.ServiceHelper
import ru.nsu.reciepebook.service.TimerState
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Black75
import ru.nsu.reciepebook.ui.theme.ReciepeBookTheme
import ru.nsu.reciepebook.ui.theme.Typography
import ru.nsu.reciepebook.util.Constants

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun InteractiveCooking(
    uiState: CookingState,
    timerState: TimerState,
    onEvent: (CookingEvent) -> Unit,
    uiEvent: Flow<CookingViewModel.UIEvent>,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }

    TopBarWithArrow(
        title = stringResource(id = R.string.interactive_cooking),
        onBackArrow = navigateUp
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
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
            Row(
                modifier = Modifier.height(100.dp)
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(0.8f),
                    onClick = {
                        ServiceHelper.triggerCountdownService(
                            context = context,
                            action = if (timerState.state == CountdownState.Started) Constants.ACTION_SERVICE_STOP
                            else Constants.ACTION_SERVICE_START
                        )
                    }
                ) {
                    Text(
                        text = if (timerState.state == CountdownState.Started) "Stop"
                        else if ((timerState.state == CountdownState.Stopped)) "Resume"
                        else "Start"
                    )
                }
                Spacer(modifier = Modifier.width(30.dp))
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(0.8f),
                    onClick = {
                        ServiceHelper.stopCountdownService(
                            context = context
                        )
                    },
                    enabled = timerState.seconds != "00" &&
                            timerState.state != CountdownState.Started
                ) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}
@Composable
fun AnimatedTimer(
    hours: String,
    minutes: String,
    seconds: String
) {
    Row {
        AnimatedTimeUnit(
            units = hours,
            color = if (hours == "00") Black75 else Black500
        )
        Text(
            text = ":",
            style = TextStyle(
                fontSize = Typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = Black500
            )
        )
        AnimatedTimeUnit(
            units = minutes,
            color = if (minutes == "00" && hours == "00") Black75 else Black500
        )
        Text(
            text = ":",
            style = TextStyle(
                fontSize = Typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = Black500
            )
        )
        AnimatedTimeUnit(
            units = seconds,
            color = if (seconds == "00" && minutes == "00" && hours == "00") Black75 else Black500
        )
    }
}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedTimeUnit(
    units: String,
    color: Color = Black500
) {
    AnimatedContent(targetState = units, transitionSpec = { addAnimation() },
        label = ""
    ) {
        Text(
            text = it,
            style = TextStyle(
                fontSize = Typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold,
                color = color
            )
        )
    }
}

@ExperimentalAnimationApi
fun addAnimation(duration: Int = 600): ContentTransform {
    return (slideInVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    )).togetherWith(slideOutVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    ))
}
@Composable
@Preview
fun test() {
    ReciepeBookTheme {
        InteractiveCooking(CookingState(),
            TimerState("00", "00", "00"), {},flow {}, {})
    }
}