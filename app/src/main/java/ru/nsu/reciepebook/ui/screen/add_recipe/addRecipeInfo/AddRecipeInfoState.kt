package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

import android.net.Uri
import ru.nsu.reciepebook.util.Constants.Companion.ALL_TAGS

data class AddRecipeInfoState(
    val name: String = "",
    val description: String = "",
    val timeInSeconds: Long = 0,
    val kcal: Long = 0,
    val itemsComplexity: List<String> = listOf("Легко", "Средне", "Сложно"),
    val selectedIndexComplexity: Int = 0,
    val itemsType: List<String> = listOf("Завтрак", "Обед", "Ужин", "Десерт"),
    val selectedIndexType: Int = 0,
    val displayedTags: List<String> = emptyList(),
    val suggestedTags: List<String> = ALL_TAGS.take(5),
    val tagInput: String = "",
    val selectedImageUri: Uri? = null,
)