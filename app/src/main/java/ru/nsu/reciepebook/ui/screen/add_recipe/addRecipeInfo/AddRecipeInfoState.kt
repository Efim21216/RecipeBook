package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

data class AddRecipeInfoState(
    val name: String = "",
    val description: String = "",
    val timeInSeconds: Long = 0,
    val kcal: Long = 10,
    var expandedComplexity: Boolean = false,
    val itemsComplexity: List<String> = listOf("Легко", "Средне", "Сложно"),
    var selectedIndexComplexity: Int = 0,
    var expandedType: Boolean = false,
    val itemsType: List<String> = listOf("Завтрак", "Обед", "Ужин", "Десерт"),
    var selectedIndexType: Int = 0,
    var tags: List<String> = listOf("#Быстрее", "#ВеликолепныйОчень"),
    var tagInput: String = "",
)