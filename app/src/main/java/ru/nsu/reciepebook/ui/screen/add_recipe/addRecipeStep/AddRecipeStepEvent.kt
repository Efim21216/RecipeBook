package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep

import android.net.Uri
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep.AddRecipeStepEvent

sealed class AddRecipeStepEvent {
    data class OnChangeName(val value: String) : AddRecipeStepEvent()
    data class OnChangeDescription(val value: String) : AddRecipeStepEvent()
    data class OnChangeTime(val value: Int) : AddRecipeStepEvent()
    data class OnImageChange(val value: Uri?) : AddRecipeStepEvent()
    data object OnDone : AddRecipeStepEvent()
    data object AddStep: AddRecipeStepEvent()
    data class ToStep(val value: Int): AddRecipeStepEvent()

}