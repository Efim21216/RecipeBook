package ru.nsu.reciepebook.ui.screen.authorization

import ru.nsu.reciepebook.ui.screen.registration.RegistrationEvent

sealed class AuthorizationEvent {
    data class OnChangeEmail(val value: String): AuthorizationEvent()
    data class OnChangePassword(val value: String): AuthorizationEvent()
    data object Authorize: AuthorizationEvent()
    data object ToReg: AuthorizationEvent()
}