package ru.nsu.reciepebook.data.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.data.model.RecipeInfoDTO
import ru.nsu.reciepebook.data.repository.MainRepository
import ru.nsu.reciepebook.util.JwtTokenManager
import ru.nsu.reciepebook.util.Resource
import java.io.IOException
import javax.inject.Inject

class GetRecipeInfoUseCase @Inject constructor(
    val repository: MainRepository,
    val jwtTokenManager: JwtTokenManager
) {
    operator fun invoke(recipeId: Int): Flow<Resource<RecipeInfoDTO>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getRecipeInfo(jwtTokenManager.getAccessJwt()!!, recipeId)
            if (response.isSuccessful) emit(Resource.Success(response.body()!!))
            else emit(Resource.Error("Ошибка при получении"))
        } catch(e: IOException) {
            emit(Resource.Error("Проверьте интернет соединение"))
        } catch(e: Exception) {
            emit(Resource.Error("Технические неполадки"))
        }
    }
}