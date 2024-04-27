import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import ru.nsu.reciepebook.ui.screen.cooking.CookingInfoState
import ru.nsu.reciepebook.ui.screen.recipeInfo.RecipeInfoEvent
import ru.nsu.reciepebook.ui.screen.recipeInfo.RecipeInfoState
import ru.nsu.reciepebook.ui.screen.recipeInfo.RecipeInfoViewModel

@Composable
fun CookingInfo(
    uiState: CookingInfoState,
    onEvent: (CookingInfoState) -> Unit,
    uiEvent: Flow<RecipeInfoViewModel.UIEvent>,
    navigateUp: () -> Unit
){

}