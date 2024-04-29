package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep

import android.net.Uri
import ru.nsu.reciepebook.util.Constants
import ru.nsu.reciepebook.util.Constants.Companion.ALL_TAGS

data class AddRecipeStepState (
    val steps: List<StepInfo> = emptyList(),
    val currentStep: Int = 0
)
data class StepInfo(
    val name: String = "",
    val description: String = "",
    val timeInSeconds: Int = 0,
    val selectedImageUri: Uri? = null,
)