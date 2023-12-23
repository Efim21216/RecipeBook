package ru.nsu.reciepebook.data.repository

import retrofit2.Response
import ru.nsu.reciepebook.data.api.ApiService
import ru.nsu.reciepebook.data.model.AuthResponse
import ru.nsu.reciepebook.data.model.User
import ru.nsu.reciepebook.util.Constants

class MainRepository(private val api: ApiService) {
    suspend fun login(user: User): Response<AuthResponse> {
        return api.login(user)
    }
    suspend fun register(user: User): Response<AuthResponse> {
        return api.register(user)
    }
    suspend fun refresh(token: String): Response<AuthResponse> {
        return api.refresh(Constants.AUTH + token)
    }
}