package ru.nsu.reciepebook.ui.screen.myRecipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import ru.nsu.reciepebook.R
import ru.nsu.reciepebook.ui.Screen
import ru.nsu.reciepebook.ui.components.TopBarWithArrow

@Composable
fun MyRecipes(
    navController: NavHostController
) {
    TopBarWithArrow(
        title = stringResource(id = R.string.my_recipes),
        onBackArrow = {
            navController.navigateUp()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                navController.navigate(Screen.RecipeInfoScreen.route + "/0")
            }) {
                Text(text = "To recipe info")
            }
        }
    }
}