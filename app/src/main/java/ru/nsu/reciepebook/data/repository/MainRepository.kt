package ru.nsu.reciepebook.data.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import ru.nsu.reciepebook.data.api.ApiService
import ru.nsu.reciepebook.data.model.AuthResponse
import ru.nsu.reciepebook.data.model.RecipeInfoDTO
import ru.nsu.reciepebook.data.model.StepDTO
import ru.nsu.reciepebook.data.model.User
import ru.nsu.reciepebook.util.Constants
import java.io.File

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

    suspend fun uploadRecipeImage(token: String, image: File, id: Int): Response<String> {
        return api.uploadRecipeImage(
            Constants.AUTH + token,
            id,
            MultipartBody.Part.createFormData(
                "image",
                image.name,
                image.asRequestBody()
            )
        )
    }

    suspend fun createRecipeInfo(token: String, recipeInfoDTO: RecipeInfoDTO): Response<RecipeInfoDTO> {
        return api.createRecipeInfo(
            Constants.AUTH + token,
            recipeInfoDTO
        )
    }
    suspend fun createStep(token: String, step: StepDTO): Response<StepDTO> {
        return api.createStep(token, step)
    }
    suspend fun confirmRecipe(token: String, id: Int): Response<String> {
        return api.confirmRecipe(token, id)
    }
}