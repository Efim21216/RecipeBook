package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

import android.net.Uri

sealed class AddRecipeInfoEvent {
    data class OnChangeName(val value: String): AddRecipeInfoEvent()
    data class OnChangeDescription(val value: String): AddRecipeInfoEvent()
    data class OnChangeTime(val value: Long): AddRecipeInfoEvent()
    data class OnChangeKcal(val value: Long): AddRecipeInfoEvent()
    data class OnChangeComplexity(val value: Int): AddRecipeInfoEvent()
    data class OnChangeType(val value: Int): AddRecipeInfoEvent()
    data class OnChangeInputTag(val value: String): AddRecipeInfoEvent()
    data class OnRemoveTag(val value: String): AddRecipeInfoEvent()
    data class OnAddTag(val value: String): AddRecipeInfoEvent()
    data object OnClearTag : AddRecipeInfoEvent()
    data class OnImageChange(val value: Uri?): AddRecipeInfoEvent()

}