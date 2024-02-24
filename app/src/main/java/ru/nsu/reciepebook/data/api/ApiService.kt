package ru.nsu.reciepebook.data.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import ru.nsu.reciepebook.data.model.AuthResponse
import ru.nsu.reciepebook.data.model.RecipeInfo
import ru.nsu.reciepebook.data.model.User

interface ApiService {
    @POST("/auth/authenticate")
    suspend fun login(@Body user: User): Response<AuthResponse>
    @POST("/auth/register")
    suspend fun register(@Body user: User): Response<AuthResponse>
    @POST("/auth/refresh-token")
    suspend fun refresh(@Header("Authorization") token: String): Response<AuthResponse>

    @Multipart
    @POST("/image")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    )
    @POST
    suspend fun createRecipeInfo(
        @Header("Authorization") token: String,
        @Body recipeInfo: RecipeInfo
    ): Response<Unit>
}