package ru.nsu.reciepebook.data.use_cases

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.data.repository.MainRepository
import ru.nsu.reciepebook.util.JwtTokenManager
import ru.nsu.reciepebook.util.Resource
import java.io.File
import java.io.IOException
import javax.inject.Inject

class UploadRecipeImageUseCase @Inject constructor(
    private val repository: MainRepository,
    private val jwtTokenManager: JwtTokenManager
) {
    operator fun invoke(image: File, recipeId: Int): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            Log.d("MyTag", "SEND REQUEST")
            val response = repository.uploadRecipeImage(jwtTokenManager.getAccessJwt()!!, image, recipeId)
            Log.d("MyTag", "DONE REQUEST")
            if (response.isSuccessful) emit(Resource.Success(response.body()!!))
            else emit(Resource.Error("Ошибка при отправке"))
        } catch(e: IOException) {
            Log.d("MyTag", "err - ${e.message}")
            Log.d("MyTag", e.toString())
            emit(Resource.Error("Проверьте интернет соединение"))
        } catch(e: Exception) {
            Log.d("MyTag", "err - ${e.message}")
            Log.d("MyTag", e.toString())
            emit(Resource.Error("Технические неполадки"))
        }
    }
}