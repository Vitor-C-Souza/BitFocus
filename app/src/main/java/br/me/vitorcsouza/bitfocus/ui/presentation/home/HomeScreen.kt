package br.me.vitorcsouza.bitfocus.ui.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.me.vitorcsouza.bitfocus.ui.components.BitFocusButton
import br.me.vitorcsouza.bitfocus.ui.components.BitTimerComponent
import br.me.vitorcsouza.bitfocus.ui.theme.DeepCharcoal
import br.me.vitorcsouza.bitfocus.ui.theme.TextGray
import br.me.vitorcsouza.bitfocus.ui.theme.White

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    HomeScreenContent(
        state = state,
        onToggleTimer = { viewModel.toggleTimer() }
    )
}

@Composable
private fun HomeScreenContent(
    state: HomeStates,
    onToggleTimer: () -> Unit
) {
    Scaffold(
        containerColor = DeepCharcoal
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "BitFocus",
                style = MaterialTheme.typography.headlineMedium
            )

            BitTimerComponent(
                progress = state.progress,
                timerDisplay = state.timerDisplay
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "CURRENT GOAL",
                    color = TextGray,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(state.currentGoal, color = White, style = MaterialTheme.typography.titleLarge)
            }

            BitFocusButton(
                onClick = onToggleTimer,
                text = if (state.isRunning) "Stop" else "Start"
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    // Using HomeScreenContent instead of HomeScreen to avoid ViewModel instantiation in Preview
    HomeScreenContent(
        state = HomeStates(),
        onToggleTimer = {}
    )
}