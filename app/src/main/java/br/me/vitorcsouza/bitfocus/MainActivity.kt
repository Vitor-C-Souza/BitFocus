package br.me.vitorcsouza.bitfocus

import android.Manifest
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.me.vitorcsouza.bitfocus.data.service.FocusNotificationBlocker
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
        hideSystemUI()

        createNotificationChannel(this)
        requestNotificationPermission()
        checkNotificationPermissions()

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

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
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

    private fun checkNotificationPermissions() {
        val componentName = ComponentName(this, FocusNotificationBlocker::class.java)
        val enabledListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners")
        val isListenerEnabled = enabledListeners?.contains(componentName.flattenToString()) == true

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val isPolicyEnabled =
            notificationManager.isNotificationPolicyAccessGranted

        if (!isListenerEnabled) {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        } else if (!isPolicyEnabled) {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS))
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
