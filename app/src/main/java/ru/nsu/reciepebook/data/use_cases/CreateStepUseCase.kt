package ru.nsu.reciepebook.data.use_cases

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.data.model.StepDTO
import ru.nsu.reciepebook.data.repository.MainRepository
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep.AddRecipeStepState
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep.StepInfo
import ru.nsu.reciepebook.util.JwtTokenManager
import ru.nsu.reciepebook.util.Resource
import java.io.IOException
import javax.inject.Inject

class CreateStepUseCase @Inject constructor(
    private val repository: MainRepository,
    private val jwtTokenManager: JwtTokenManager
) {
    operator fun invoke(addRecipeStepState: AddRecipeStepState, recipeId: Int): Flow<Resource<StepDTO>> = flow {
        try {
            emit(Resource.Loading())
            val step = with(addRecipeStepState.steps[addRecipeStepState.currentStep]) {
                StepDTO(
                    number = addRecipeStepState.currentStep,
                    name = name,
                    description = description,
                    recipeId = recipeId,
                    timerInSeconds = timeInSeconds)
            }
            val response = repository.createStep(jwtTokenManager.getAccessJwt()!!, step)
            if (response.isSuccessful) emit(Resource.Success(response.body()!!))
            else emit(Resource.Error("Ошибка при создании"))
        } catch(e: IOException) {
            emit(Resource.Error("Проверьте интернет соединение"))
        } catch(e: Exception) {
            Log.d("MyTag", "ex - ${e.message}")
            emit(Resource.Error("Технические неполадки"))
        }
    }
}