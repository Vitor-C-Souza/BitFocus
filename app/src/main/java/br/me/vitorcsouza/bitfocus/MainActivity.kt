package br.me.vitorcsouza.bitfocus

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.me.vitorcsouza.bitfocus.ui.navigation.BitFocusNavGraph
import br.me.vitorcsouza.bitfocus.ui.theme.BitFocusTheme
import br.me.vitorcsouza.bitfocus.utils.NotificationHelper.createNotificationChannel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        createNotificationChannel(this)
        requestNotificationPermission()

        setContent {
            BitFocusTheme {
                val navController = rememberNavController()
                
                LaunchedEffect(intent) {
                    handleIntent(intent, navController)
                }

                BitFocusNavGraph(
                    navController = navController
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    private fun handleIntent(intent: Intent?, navController: NavHostController) {
        intent?.let {
            if (it.getStringExtra("navigate_to") == "completion") {
                val goal = it.getStringExtra("goal") ?: "Focus"
                val duration = it.getIntExtra("duration", 25)
                navController.navigate("completion/$duration/$goal") {
                    popUpTo("setup") { inclusive = false }
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BitFocusAppPreview() {
    BitFocusTheme {
        val navController = rememberNavController()
        BitFocusNavGraph(navController = navController, isPreview = true)
    }
}
