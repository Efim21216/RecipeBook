package ru.nsu.reciepebook.ui.screen.filter

import ru.nsu.reciepebook.util.Constants


data class SearchFilterState (
    val displayedTags: List<String> = emptyList(),
    val suggestedTags: List<String> = Constants.ALL_TAGS.take(5),
    val tagInput: String = ""
)