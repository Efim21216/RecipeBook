package ru.nsu.reciepebook.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

val LocalSnackbarHostState = compositionLocalOf {
    SnackbarHostState()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationWrapper(
    navController: NavHostController,
    title: String,
    isShowBottomBar: Boolean,
    content: @Composable (BoxScope.() -> Unit)
) {
    val snackbarHostState = LocalSnackbarHostState.current
    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = { if (isShowBottomBar) BottomNavigationBar(navController = navController)},
            topBar = {
                TopAppBar(title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineLarge
                    )
                })
            }
        ) {
            Box(
                modifier = Modifier.padding(it),
                content = content
            )
        }
    }

}