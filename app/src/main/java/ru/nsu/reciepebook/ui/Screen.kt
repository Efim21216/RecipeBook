package ru.nsu.reciepebook.ui

sealed class Screen(val route: String) {
    data object AuthorizationScreen: Screen(route = "auth_screen")
    data object RegistrationScreen: Screen(route = "reg_screen")
    data object HomeScreen: Screen(route = "home_screen")
    data object ProfileScreen: Screen(route = "profile_screen")
    data object SearchScreen: Screen(route = "search_screen")
    data object AuthGraph: Screen(route = "auth_graph")
    data object MainGraph: Screen(route = "main_graph")
}