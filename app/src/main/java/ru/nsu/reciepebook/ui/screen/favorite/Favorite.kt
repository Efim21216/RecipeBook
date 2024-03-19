package ru.nsu.reciepebook.ui.screen.favorite

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.service.CountdownService
import ru.nsu.reciepebook.service.CountdownState
import ru.nsu.reciepebook.service.ServiceHelper
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.theme.Black75
import ru.nsu.reciepebook.ui.theme.Typography
import ru.nsu.reciepebook.util.Constants.Companion.ACTION_SERVICE_CANCEL
import ru.nsu.reciepebook.util.Constants.Companion.ACTION_SERVICE_START
import ru.nsu.reciepebook.util.Constants.Companion.ACTION_SERVICE_STOP

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Favorite(
    uiState: FavoriteState,
    onEvent: (FavoriteEvent) -> Unit,
    uiEvent: Flow<FavoriteViewModel.UIEvent>,
    navigateUp: () -> Unit,
    countdownService: CountdownService
) {
    val context = LocalContext.current
    val hours by countdownService.hours
    val minutes by countdownService.minutes
    val seconds by countdownService.seconds
    val currentState by countdownService.currentState

    LaunchedEffect(key1 = true) {
        if (countdownService.currentState.value == CountdownState.Idle)
            countdownService.setTime(uiState.duration)
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }

    TopBarWithArrow(
        title = stringResource(id = R.string.favorite),
        onBackArrow = navigateUp
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(weight = 9f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedContent(targetState = hours, transitionSpec = { addAnimation() },
                    label = ""
                ) {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = Typography.headlineLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = if (hours == "00") Black75 else Blue
                        )
                    )
                }
                AnimatedContent(targetState = minutes, transitionSpec = { addAnimation() },
                    label = ""
                ) {
                    Text(
                        text = it, style = TextStyle(
                            fontSize = Typography.headlineLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = if (minutes == "00" && hours == "00") Black75 else Blue
                        )
                    )
                }
                AnimatedContent(targetState = seconds, transitionSpec = { addAnimation() },
                    label = ""
                ) {
                    Text(
                        text = it, style = TextStyle(
                            fontSize = Typography.headlineLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = if (seconds == "00" && minutes == "00" && hours == "00") Black75 else Blue
                        )
                    )
                }
            }
            Row(modifier = Modifier.weight(weight = 1f)) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(0.8f),
                    onClick = {
                        ServiceHelper.triggerCountdownService(
                            context = context,
                            action = if (currentState == CountdownState.Started) ACTION_SERVICE_STOP
                            else ACTION_SERVICE_START
                        )
                    }
                ) {
                    Text(
                        text = if (currentState == CountdownState.Started) "Stop"
                        else if ((currentState == CountdownState.Stopped)) "Resume"
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
                    enabled = seconds != "00" && currentState != CountdownState.Started
                ) {
                    Text(text = "Cancel")
                }
            }
        }
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