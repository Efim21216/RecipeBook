package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep

import android.net.Uri
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeStep.AddRecipeStepEvent

sealed class AddRecipeStepEvent {
    data class OnChangeName(val value: String) : AddRecipeStepEvent()
    data class OnChangeDescription(val value: String) : AddRecipeStepEvent()
    data class OnChangeTime(val value: Long) : AddRecipeStepEvent()
    data class OnChangeKcal(val value: Long) : AddRecipeStepEvent()
    data class OnChangeComplexity(val value: Int) : AddRecipeStepEvent()
    data class OnChangeType(val value: Int) : AddRecipeStepEvent()
    data class OnChangeInputTag(val value: String) : AddRecipeStepEvent()
    data class OnRemoveTag(val value: String) : AddRecipeStepEvent()
    data class OnAddTag(val value: String) : AddRecipeStepEvent()
    data object OnClearTag : AddRecipeStepEvent()
    data class OnImageChange(val value: Uri?) : AddRecipeStepEvent()
    data object OnDone : AddRecipeStepEvent()

}