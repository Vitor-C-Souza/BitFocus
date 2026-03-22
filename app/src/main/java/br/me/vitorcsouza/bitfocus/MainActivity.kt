package br.me.vitorcsouza.bitfocus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import br.me.vitorcsouza.bitfocus.ui.navigation.BitFocusNavGraph
import br.me.vitorcsouza.bitfocus.ui.theme.BitFocusTheme
import br.me.vitorcsouza.bitfocus.utils.NotificationHelper.createNotificationChannel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        createNotificationChannel(this)

        setContent {
            BitFocusTheme {
                val navController = rememberNavController()
                BitFocusNavGraph(
                    navController = navController
                )
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
