package ru.nsu.reciepebook.data.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import retrofit2.Response
import ru.nsu.reciepebook.data.api.ApiService
import ru.nsu.reciepebook.data.model.AuthResponse
import ru.nsu.reciepebook.data.model.User
import ru.nsu.reciepebook.util.Constants
import java.io.File
import java.io.IOException

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
    suspend fun uploadImage(image: File): Boolean {
        return try {
            api.uploadImage(
                MultipartBody.Part.createFormData(
                    "image",
                    image.name,
                    image.asRequestBody()
                )
            )
            true
        } catch (e: HttpException) {
            false
        } catch (e: IOException) {
            false
        }
    }
}