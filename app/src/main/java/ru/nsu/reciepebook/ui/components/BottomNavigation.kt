package ru.nsu.reciepebook.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

val LocalSnackbarHostState = compositionLocalOf {
    SnackbarHostState()
}
@Composable
fun BottomNavigation(
    navController: NavHostController,
    content: @Composable (BoxScope.() -> Unit)
) {
    val snackbarHostState = LocalSnackbarHostState.current
    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = { BottomNavigationBar(navController = navController) },
        ) {
            Box(
                modifier = Modifier.padding(it),
                content = content
            )
        }
    }

}