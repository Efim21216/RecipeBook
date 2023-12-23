package ru.nsu.reciepebook.ui

sealed class Screen(val route: String) {
    data object AuthorizationScreen: Screen(route = "auth_screen")
    data object RegistrationScreen: Screen(route = "reg_screen")
    data object SecretScreen: Screen(route = "secret_screen")
}