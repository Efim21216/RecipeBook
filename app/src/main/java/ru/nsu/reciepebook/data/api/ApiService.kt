package ru.nsu.reciepebook.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ru.nsu.reciepebook.data.model.AuthResponse
import ru.nsu.reciepebook.data.model.User

interface ApiService {
    @POST("/auth/authenticate")
    suspend fun login(@Body user: User): Response<AuthResponse>
    @POST("/auth/register")
    suspend fun register(@Body user: User): Response<AuthResponse>
    @POST("/auth/refresh-token")
    suspend fun refresh(@Header("Authorization") token: String): Response<AuthResponse>
}