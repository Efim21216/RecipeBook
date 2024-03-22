package ru.nsu.reciepebook.ui.screen.add_recipe.addRecipeInfo

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Green200
import ru.nsu.reciepebook.ui.theme.Typography


@Composable
fun PhotoPickerButtons(
    singlePhotoPickerLauncher: ActivityResultLauncher<PickVisualMediaRequest>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green200
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add_photo_svgrepo_com_1),
                contentDescription = "Добавить фото",
                tint = Black500
            )
            Text(
                text = stringResource(id = R.string.add_photo),
                style = Typography.bodyLarge,
                color = Black500
            )
        }
    }
}