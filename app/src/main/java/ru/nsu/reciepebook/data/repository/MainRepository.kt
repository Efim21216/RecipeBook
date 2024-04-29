package ru.nsu.reciepebook.data.repository

import android.util.Log
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        return api.createStep(Constants.AUTH + token, step)
    }
    suspend fun confirmRecipe(token: String, id: Int): Response<String> {
        return api.confirmRecipe(Constants.AUTH + token, id)
    }
    suspend fun uploadStepImage(token: String, image: File, id: Int, number: Int): Response<Unit> {
        return api.uploadStepImage(
            token = Constants.AUTH + token,
            id = id,
            number = number,
            image = MultipartBody.Part.createFormData(
                "image",
                image.name,
                image.asRequestBody()
            )
        )
    }
    suspend fun getRecipeInfo(token: String, id: Int): Response<RecipeInfoDTO> {
        return api.getRecipeInfo(
            token = Constants.AUTH + token,
            id = id
        )
    }
    suspend fun getMyRecipes(token: String): Response<List<RecipeInfoDTO>> {
        return api.getMyRecipes(
            token = Constants.AUTH + token
        )
    }
    suspend fun getSteps(token: String, id: Int): Response<List<StepDTO>> {
        return api.getSteps(
            token = Constants.AUTH + token,
            id = id
        )
    }
}