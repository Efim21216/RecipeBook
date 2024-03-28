package ru.nsu.reciepebook.data.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.data.repository.MainRepository
import ru.nsu.reciepebook.util.JwtTokenManager
import ru.nsu.reciepebook.util.Resource
import java.io.IOException
import javax.inject.Inject

class ConfirmRecipeUseCase @Inject constructor(
    private val repository: MainRepository,
    private val jwtTokenManager: JwtTokenManager
) {
    operator fun invoke(recipeId: Int): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.confirmRecipe(jwtTokenManager.getAccessJwt()!!, recipeId)
            if (response.isSuccessful) emit(Resource.Success(response.body()!!))
            else emit(Resource.Error("Ошибка при создании"))
        } catch(e: IOException) {
            emit(Resource.Error("Проверьте интернет соединение"))
        } catch(e: Exception) {
            emit(Resource.Error("Технические неполадки"))
        }
    }
}