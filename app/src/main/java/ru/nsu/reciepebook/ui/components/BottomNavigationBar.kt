package ru.nsu.reciepebook.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.theme.Black50
import ru.nsu.reciepebook.ui.theme.Primary200
import ru.nsu.reciepebook.ui.theme.PurpleGrey80


sealed class BottomBarItem(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    data object Home : BottomBarItem(
        route = Screen.HomeScreen.route,
        title = "Главная",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    )

    data object Profile : BottomBarItem(
        route = Screen.ProfileScreen.route,
        title = "Профиль",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
    )

    data object Search : BottomBarItem(
        route = Screen.SearchScreen.route,
        title = "Поиск",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
    )
}
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarItem.Home,
        BottomBarItem.Profile,
        BottomBarItem.Search,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val itemColors = NavigationBarItemDefaults.colors(
        indicatorColor = Primary200,
        selectedIconColor = Color.White,
        selectedTextColor = Color.White,
        unselectedIconColor = Black50,
        unselectedTextColor = Black50
    )
    NavigationBar(
        containerColor = Primary200
    ) {
        screens.forEach{ item ->
            NavigationBarItem(
                label = {Text(text = item.title)},
                selected = isSelected(currentDestination, item),
                colors = itemColors,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items

                        popUpTo(Screen.HomeScreen.route) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    if (isSelected(currentDestination, item))
                        Icon(imageVector = item.selectedIcon, contentDescription = item.title)
                    else Icon(imageVector = item.unselectedIcon, contentDescription = item.title)
                })
        }
    }
}
fun isSelected(currentDestination: NavDestination?, item: BottomBarItem) =
    currentDestination?.hierarchy?.any { it.route == item.route } == true
@Preview
@Composable
fun bar() {
    val screens = listOf(
        BottomBarItem.Home,
        BottomBarItem.Profile,
        BottomBarItem.Search,
    )
    NavigationBar(
        containerColor = Primary200
    ) {
        screens.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier.height(75.dp),
                label = {Text(text = item.title)},
                selected = index == 0,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Primary200,
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = PurpleGrey80,
                    unselectedTextColor = Color.White
                ),
                onClick = { },
                icon = { if (0 == index) {
                    Icon(imageVector = item.selectedIcon, contentDescription = item.title)
                } else Icon(imageVector = item.unselectedIcon, contentDescription = item.title)
                })
        }
    }
}

