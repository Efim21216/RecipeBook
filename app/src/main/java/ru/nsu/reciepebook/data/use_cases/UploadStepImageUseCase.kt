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

class UploadStepImageUseCase @Inject constructor(
    val repository: MainRepository,
    val jwtTokenManager: JwtTokenManager
) {
    operator fun invoke(image: File, recipeId: Int, number: Int): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.uploadStepImage(jwtTokenManager.getAccessJwt()!!, image, recipeId, number)
            if (response.isSuccessful) emit(Resource.Success(response.body()!!))
            else emit(Resource.Error("Ошибка при отправке"))
        } catch(e: IOException) {
            emit(Resource.Error("Проверьте интернет соединение"))
        } catch(e: Exception) {
            Log.d("MyTag", "ex - ${e.message}")
            emit(Resource.Error("Технические неполадки"))
        }
    }
}