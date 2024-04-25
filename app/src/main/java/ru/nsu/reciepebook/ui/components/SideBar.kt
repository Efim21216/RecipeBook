package ru.nsu.reciepebook.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Primary300
import ru.nsu.reciepebook.ui.theme.Primary50
import ru.nsu.reciepebook.ui.theme.Primary500
import ru.nsu.reciepebook.ui.theme.Typography


@Composable
fun SideBar(
    countSteps: Int,
    currentStep: Int,
    toAddIngredients: () -> Unit = {},
    toAddInfo: () -> Unit = {},
    isCooking: Boolean = false,
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
            modifier = Modifier.clickable { toAddInfo() })
        if (!isCooking) {
            Spacer(modifier = Modifier.height(20.dp))
            Icon(painter = painterResource(id = R.drawable.icon_ingridients),
                contentDescription = "Info",
                modifier = Modifier.clickable { toAddIngredients() })
        }
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