package ru.nsu.reciepebook.ui.screen.cooking

import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.service.TimerState
import ru.nsu.reciepebook.ui.components.SideBar
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Black75
import ru.nsu.reciepebook.ui.theme.ReciepeBookTheme
import ru.nsu.reciepebook.ui.theme.Typography

@Composable
fun InteractiveCooking(
    uiState: CookingState,
    timerState: TimerState,
    recipeId: Int,
    onEvent: (CookingEvent) -> Unit,
    uiEvent: Flow<CookingViewModel.UIEvent>,
    navigateUp: () -> Unit,
    toStep: (Int) -> Unit,
    toInfo: () -> Unit
) {
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
        if (uiState.currentStep == -1)
            return@TopBarWithArrow
        Row(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            SideBar(countSteps = uiState.steps.size,
                currentStep = uiState.currentStep,
                toAddInfo = toInfo,
                toStep = toStep,
                isCooking = true)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))
                        if (uiState.steps[uiState.currentStep].name != ""){
                        Text(

                            text = uiState.steps[uiState.currentStep].name,
                            style = Typography.headlineMedium
                        )}
                        else{
                            Text(
                                text = stringResource(id = R.string.step_N) + uiState.currentStep,
                                style = Typography.headlineMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                    }
                    if (uiState.steps[uiState.currentStep].imageUrl != null) {
                        Log.d("MyTag", "URL -- ${uiState.steps[uiState.currentStep].imageUrl}")
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(uiState.steps[uiState.currentStep].imageUrl)
                                .addHeader("Authorization", uiState.token)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            placeholder = painterResource(R.drawable.image_placeholder),
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    Text(text = uiState.steps[uiState.currentStep].text, style = Typography.bodyLarge)
                    Spacer(modifier = Modifier.height(20.dp))
                    TimerPanel(timerState = timerState, uiState.currentStep, recipeId = recipeId)
                }
                if (uiState.currentStep < uiState.steps.size - 1) {
                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        onClick = { toStep(uiState.currentStep + 1) },
                        shape = RoundedCornerShape(15.dp),) {
                        Text(text = stringResource(id = R.string.next))
                    }
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
        InteractiveCooking(CookingState(steps = listOf(StepState(1))),
            TimerState("00", "00", "00"), 1, {},flow {}, {}, {}, {})
    }
}