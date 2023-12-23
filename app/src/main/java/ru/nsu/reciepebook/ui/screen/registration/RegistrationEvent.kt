package ru.nsu.reciepebook.ui.screen.registration


sealed class RegistrationEvent {
    data class OnChangeEmail(val value: String): RegistrationEvent()
    data class OnChangePassword(val value: String): RegistrationEvent()
    data object Register: RegistrationEvent()
    data object ToAuth: RegistrationEvent()
}