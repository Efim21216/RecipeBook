package ru.nsu.reciepebook.ui.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.FilledButton
import ru.nsu.reciepebook.ui.components.OutlinedInputText
import ru.nsu.reciepebook.ui.components.TopBar
import ru.nsu.reciepebook.ui.theme.ReciepeBookTheme
import ru.nsu.reciepebook.ui.theme.Typography

@Composable
fun SearchScreen(
    uiState: SearchState,
    onEvent: (SearchEvent) -> Unit,
    uiEvent: Flow<SearchViewModel.UIEvent>
) {

    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }
    TopBar(
        title = stringResource(id = R.string.search)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedInputText(
                    value = uiState.name,
                    onValueChange = {
                        onEvent(SearchEvent.OnChangeName(it))
                    },
                    hint = stringResource(id = R.string.enter_name),
                    modifier = Modifier.height(55.dp)
                )
                Icon(
                    modifier = Modifier
                        .height(40.dp)
                        .width(35.dp)
                        .clickable { },
                    painter = painterResource(id = R.drawable.icon_settings),
                    contentDescription = "Info"
                )
            }
            FilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 30.dp, 20.dp, 0.dp),
                onClick = { /*TODO*/ },
                text = stringResource(id = R.string.find)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                foodCategory(
                    text = stringResource(id = R.string.breakfast),
                    painter = painterResource(id = R.drawable.image_breakfast)
                )
                foodCategory(
                    text = stringResource(id = R.string.lunch),
                    painter = painterResource(id = R.drawable.image_lunch)
                )
            }
            Spacer(Modifier.height(50.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                foodCategory(
                    text = stringResource(id = R.string.dinner),
                    painter = painterResource(id = R.drawable.image_dinner)
                )
                foodCategory(
                    text = stringResource(id = R.string.diet_food),
                    painter = painterResource(id = R.drawable.image_diet)
                )
            }
        }
    }
}

@Composable
fun foodCategory(
    text: String,
    painter: Painter
) {
    Column {
        Text(
            text = text,
            style = Typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(15.dp))
        Image(
            painter = painter,
            modifier = Modifier
                .height(160.dp)
                .width(165.dp),
            contentDescription = text
        )
    }
}

@Preview
@Composable
fun showSearchScreen() {
    ReciepeBookTheme {
        SearchScreen(uiState = SearchState(), onEvent = {}, uiEvent = flow { })
    }
}