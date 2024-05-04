    package ru.nsu.reciepebook.ui.screen.filter

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.components.TopBarWithArrow
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.TagChip
import ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo.TagsInputField
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Green200
import ru.nsu.reciepebook.ui.theme.Primary200
import ru.nsu.reciepebook.ui.theme.Typography
import java.util.Arrays

    @Composable
fun SearchFilter(
    uiState: SearchFilterState,
    onEvent: (SearchFilterEvent) -> Unit,
    uiEvent: Flow<SearchFilterViewModel.UIEvent>,
    navigateUp: () -> Unit,
    onDone: (Array<String>) -> Unit
) {
    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                else -> {}
            }

        }
    }
    TopBarWithArrow(
        title = stringResource(id = R.string.filter),
        onBackArrow = navigateUp
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 10.dp),
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(id = R.string.specify_tags),
                    style = Typography.headlineMedium
                )
                Spacer(Modifier.height(12.dp))
                TagsInputField(
                    modifier = Modifier.padding(20.dp, 10.dp),
                    inputText = uiState.tagInput,
                    suggestedTags = uiState.suggestedTags,
                    onChange = {
                        onEvent(SearchFilterEvent.OnChangeInputTag(it))
                    },
                    addTag = {
                        onEvent(SearchFilterEvent.OnAddTag(it))
                    },
                    clearInput = {
                        onEvent(SearchFilterEvent.OnClearTag)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedBorderColor = Primary200,
                        unfocusedBorderColor = Primary200
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier.padding(20.dp, 0.dp)
                ) {
                    uiState.displayedTags.forEach { tag ->
                        TagChip(
                            tag = "#$tag",
                            onRemove = {
                                onEvent(SearchFilterEvent.OnRemoveTag(tag))
                            }
                        )
                    }
                }
            }
            Button(
                onClick = {
                    onDone(uiState.displayedTags.toTypedArray())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green200
                )
            ) {
                Text(
                    text = stringResource(id = R.string.apply),
                    style = Typography.bodyLarge,
                    color = Black500
                )
            }
        }
    }
}
    @Preview(showBackground = true)
    @Composable
    fun SearchFilterTest() {
        val li: List<String> = listOf("#быстро")
        SearchFilter(
            uiState =  SearchFilterState(displayedTags = li, suggestedTags = emptyList()),
            onEvent={},
        uiEvent= emptyFlow<SearchFilterViewModel.UIEvent>(),
        navigateUp= {  },
        onDone= {  }

        )
    }