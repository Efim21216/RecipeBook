package ru.nsu.reciepebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.reciepebook.ui.Navigation
import ru.nsu.reciepebook.ui.theme.ReciepeBookTheme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
            .setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        setContent {
            ReciepeBookTheme {
                navController = rememberNavController()
                Surface {
                    if (!viewModel.isLoading.value) {
                        Navigation(
                            navController = navController,
                            startDestination = viewModel.startScreen.value
                        )
                    }
                }
            }
        }
    }
}
