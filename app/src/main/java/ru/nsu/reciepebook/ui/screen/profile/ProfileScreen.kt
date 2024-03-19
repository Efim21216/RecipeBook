package ru.nsu.reciepebook.ui.screen.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.TopBar
import ru.nsu.reciepebook.ui.components.bottomBorder
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Black75
import ru.nsu.reciepebook.ui.theme.Primary300
import ru.nsu.reciepebook.ui.theme.Primary400
import ru.nsu.reciepebook.ui.theme.ReciepeBookTheme
import ru.nsu.reciepebook.ui.theme.Typography

@Composable
fun ProfileScreen(
    uiState: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    uiEvent: Flow<ProfileViewModel.UIEvent>,
    toMyRecipes: () -> Unit,
    toAddRecipeInfo: () -> Unit,
    toFavorite: () -> Unit
) {

    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }

    TopBar(
        title = stringResource(id = R.string.profile)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 20.dp, 10.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(painter = painterResource(
                    id = R.drawable.image_profile),
                    contentDescription = stringResource(id = R.string.profile),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape))
                Summary()
            }
            Spacer(modifier = Modifier.height(20.dp))
            MenuOption(onClick = toMyRecipes,
                text = stringResource(id = R.string.my_recipes),
                painter = painterResource(id = R.drawable.icon_my_recipes))
            MenuOption(onClick = toFavorite,
                text = stringResource(id = R.string.favorite),
                painter = painterResource(id = R.drawable.icon_favorite))
            MenuOption(onClick = toAddRecipeInfo,
                text = stringResource(id = R.string.add_recipe),
                painter = painterResource(id = R.drawable.icon_add_recipe))
            MenuOption(onClick = { /*TODO*/ },
                text = stringResource(id = R.string.subscriptions),
                painter = painterResource(id = R.drawable.icon_subscriptions))
            MenuOption(onClick = { /*TODO*/ },
                text = stringResource(id = R.string.edit_profile),
                painter = painterResource(id = R.drawable.icon_edit))
        }
    }
}

@Composable
fun Summary() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Anna", style = Typography.headlineLarge)
        Spacer(modifier = Modifier.height(7.dp))
        Text(text = "0 " +
                stringResource(id = R.string.subscribers))
        Spacer(modifier = Modifier.height(7.dp))
        Text(text = "0 " +
                stringResource(id = R.string.published_recipes))
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = { /*TODO*/ },
            border = BorderStroke(1.dp, Primary400)
        ) {
            Text(
                text = stringResource(id = R.string.publish_recipe),
                style = Typography.headlineSmall,
                color = Primary300
            )
        }

    }
}

@Composable
fun MenuOption(
    onClick: () -> Unit,
    text: String,
    painter: Painter
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                bottomBorder(1.dp.toPx(), Black75)
            },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(0.dp, 5.dp, 0.dp, 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painter,
                contentDescription = text,
                tint = Black500
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                color = Black500,
                text = text,
                style = Typography.headlineSmall
            )
        }
    }
}
@Preview
@Composable
fun PreviewProfile() {
    ReciepeBookTheme {
        ProfileScreen(
            uiState = ProfileState(),
            onEvent = {},
            uiEvent = flow {},
            toMyRecipes = { /*TODO*/ },
            toFavorite = {},
            toAddRecipeInfo = {})
    }
}