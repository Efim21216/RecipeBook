package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

sealed class AddRecipeInfoEvent {
    data class OnChangeName(val value: String): AddRecipeInfoEvent()
    data class OnChangeDescription(val value: String): AddRecipeInfoEvent()
    data class OnChangeTime(val value: Long): AddRecipeInfoEvent()
    data class OnChangeKcal(val value: Long): AddRecipeInfoEvent()
    data class OnChangeComplexity(val value: Int): AddRecipeInfoEvent()
    data class OnChangeType(val value: Int): AddRecipeInfoEvent()
    data class OnChangeTag(val value: String): AddRecipeInfoEvent()
    data class OnRemoveTag(val value: String): AddRecipeInfoEvent()
    data class OnAddTag(val value: String): AddRecipeInfoEvent()
    data object OnClearTag : AddRecipeInfoEvent()

}