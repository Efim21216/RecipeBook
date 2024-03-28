package ru.nsu.reciepebook.data.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import ru.nsu.reciepebook.data.model.AuthResponse
import ru.nsu.reciepebook.data.model.RecipeInfoDTO
import ru.nsu.reciepebook.data.model.StepDTO
import ru.nsu.reciepebook.data.model.User

interface ApiService {
    @POST("/auth/authenticate")
    suspend fun login(@Body user: User): Response<AuthResponse>
    @POST("/auth/register")
    suspend fun register(@Body user: User): Response<AuthResponse>
    @POST("/auth/refresh-token")
    suspend fun refresh(@Header("Authorization") token: String): Response<AuthResponse>

    @Multipart
    @POST("/recipe/{id}/image")
    suspend fun uploadRecipeImage(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Part image: MultipartBody.Part
    ): Response<String>
    @POST
    suspend fun createRecipeInfo(
        @Header("Authorization") token: String,
        @Body recipeInfoDTO: RecipeInfoDTO
    ): Response<RecipeInfoDTO>
    @POST
    suspend fun createStep(
        @Header("Authorization") token: String,
        @Body recipeInfoDTO: StepDTO
    ): Response<StepDTO>
    @POST("/recipe/{id}/complete")
    suspend fun confirmRecipe(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<String>
}