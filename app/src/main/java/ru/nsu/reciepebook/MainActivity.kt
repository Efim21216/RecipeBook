package ru.nsu.reciepebook

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.nsu.reciepebook.service.CountdownService
import ru.nsu.reciepebook.ui.navigation.Navigation
import ru.nsu.reciepebook.ui.theme.ReciepeBookTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel: MainViewModel by viewModels()

    private var isBound by mutableStateOf(false)
    private lateinit var countdownService: CountdownService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as CountdownService.CountdownBinder
            countdownService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, CountdownService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        installSplashScreen()
            .setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        setContent {
            ReciepeBookTheme {
                navController = rememberNavController()
                Surface {
                    if (!viewModel.isLoading.value && isBound) {
                        Navigation(
                            navController = navController,
                            startDestination = viewModel.startScreen.value,
                            countdownService = countdownService
                        )
                    }
                }
            }
        }
    }
    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }
}
