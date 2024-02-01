package ru.nsu.reciepebook.ui.screen.authorization


sealed class AuthorizationEvent {
    data class OnChangeEmail(val value: String): AuthorizationEvent()
    data class OnChangePassword(val value: String): AuthorizationEvent()
    data object Authorize: AuthorizationEvent()
}