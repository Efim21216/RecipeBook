package ru.nsu.reciepebook.data.model

data class AuthResponse(
    val access_token: String,
    val refresh_token: String
)