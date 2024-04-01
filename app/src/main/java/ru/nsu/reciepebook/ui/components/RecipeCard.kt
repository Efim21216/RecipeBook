package ru.nsu.reciepebook.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.screen.home.ShortRecipeInfo
import ru.nsu.reciepebook.ui.theme.Black500
import ru.nsu.reciepebook.ui.theme.Black75
import ru.nsu.reciepebook.ui.theme.Typography

@Composable
fun RecipeCard(
    shortRecipeInfo: ShortRecipeInfo
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 10.dp)
            .heightIn(max = 200.dp)
            .advancedShadow(
                offsetX = 2.dp, offsetY = 4.dp,
                shadowBlurRadius = 3.dp, color = Black75, cornersRadius = 5.dp
            ),
        shape = RoundedCornerShape(0.dp, 0.dp, 5.dp, 5.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RecipeSummary(
                    profileImage = shortRecipeInfo.profileImage,
                    title = shortRecipeInfo.title,
                    author = shortRecipeInfo.author,
                    modifier = Modifier.weight(1f)
                )
                AsyncImage(
                    modifier = Modifier
                        .height(120.dp)
                        .width(180.dp),
                    model = shortRecipeInfo.previewImage,
                    error = painterResource(id = R.drawable.image_default_preview),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.image_default_preview),
                )
            }
            Text(
                text = shortRecipeInfo.description,
                color = Black500,
                style = Typography.bodyMedium,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }

}
@Composable
fun RecipeSummary(
    profileImage: String,
    title: String,
    author: String,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .then(modifier),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(15.dp, 15.dp, 0.dp, 0.dp)
        ) {
            AsyncImage(
                model = profileImage,
                contentDescription = "profile",
                placeholder = painterResource(id = R.drawable.image_profile),
                error = painterResource(id = R.drawable.image_profile),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Text(
                text = author,
                color = Black500,
                style = Typography.headlineSmall,
                modifier = Modifier
                    .padding(15.dp, 0.dp, 0.dp, 0.dp)
            )
        }
        Text(
            modifier = Modifier
                .width(170.dp)
                .padding(15.dp, 0.dp, 0.dp, 10.dp),
            text = title,
            color = Black500,
            style = Typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
