package ru.nsu.reciepebook.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.nsu.reciepebook.ui.theme.Primary300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
            )
        },
        content = content
    )
}

@Preview
@Composable
fun ShowTopBar() {
    TopBar(title = "Title") {
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithArrow(
    title: String,
    onBackArrow: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                modifier = Modifier.shadow(elevation = 5.dp),
                navigationIcon = {
                    IconButton(onClick = onBackArrow) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                            tint = Primary300
                        )
                    }
                }
            )
        },
        content = content
    )
}