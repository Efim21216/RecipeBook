package ru.nsu.reciepebook.data.use_cases

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.nsu.reciepebook.data.model.IngredientDTO
import ru.nsu.reciepebook.data.model.RecipeInfoDTO
import ru.nsu.reciepebook.data.repository.MainRepository
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.AddRecipeInfoState
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeIngredients.AddRecipeIngredientsState
import ru.nsu.reciepebook.util.Constants
import ru.nsu.reciepebook.util.JwtTokenManager
import ru.nsu.reciepebook.util.Resource
import java.io.IOException
import javax.inject.Inject

class CreateRecipeUseCase @Inject constructor(
    private val repository: MainRepository,
    private val jwtTokenManager: JwtTokenManager
) {
    operator fun invoke(recipeInfo: AddRecipeInfoState,
                        ingredientsState: AddRecipeIngredientsState): Flow<Resource<RecipeInfoDTO>> = flow {
        try {
            emit(Resource.Loading())
            val recipeInfoDTO = with(recipeInfo) {
                RecipeInfoDTO(
                    name = name,
                    description = description,
                    ingredients = ingredientsState.ingredients.map {
                        val measure = when (it.measure) {
                            0 -> Constants.Measures.KILOGRAM
                            1 -> Constants.Measures.GRAM
                            2 -> Constants.Measures.MILLIGRAM
                            3 -> Constants.Measures.LITER
                            4 -> Constants.Measures.MILLILITER
                            5 -> Constants.Measures.TEE_SPOON
                            6 -> Constants.Measures.TABLE_SPOON
                            7 -> Constants.Measures.PIECE
                            else -> {
                                Log.e("MyTag", "UNKNOWN MEASURE - ${it.measure}")
                                Constants.Measures.PIECE
                            }
                        }
                        IngredientDTO(
                            name = it.name,
                            weight = it.quantity,
                            countUnit = measure.value)
                    })
            }
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