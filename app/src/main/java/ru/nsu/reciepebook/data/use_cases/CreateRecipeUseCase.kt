package ru.nsu.reciepebook.data.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.data.model.RecipeInfoDTO
import ru.nsu.reciepebook.data.repository.MainRepository
import ru.nsu.reciepebook.util.JwtTokenManager
import ru.nsu.reciepebook.util.Resource
import java.io.IOException
import javax.inject.Inject

class CreateRecipeUseCase @Inject constructor(
    private val repository: MainRepository,
    private val jwtTokenManager: JwtTokenManager
) {
    operator fun invoke(recipeInfoDTO: RecipeInfoDTO): Flow<Resource<RecipeInfoDTO>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.createRecipeInfo(jwtTokenManager.getAccessJwt()!!, recipeInfoDTO)
            if (response.isSuccessful) emit(Resource.Success(response.body()!!))
            else emit(Resource.Error("Ошибка при создании"))
        } catch(e: IOException) {
            emit(Resource.Error("Проверьте интернет соединение"))
        } catch(e: Exception) {
            emit(Resource.Error("Технические неполадки"))
        }
    }
}