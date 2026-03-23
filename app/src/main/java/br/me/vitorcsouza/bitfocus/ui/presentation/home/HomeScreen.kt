package br.me.vitorcsouza.bitfocus.ui.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    viewModel: HomeViewModel = hiltViewModel(),
    onFinished: () -> Unit = {},
    onOpenAnalytics: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.sessionComplete) {
        if (state.sessionComplete) {
            onFinished()
            viewModel.onNavigatedToCompletion()
        }
    }

    HomeScreenContent(
        state = state,
        onToggleTimer = { viewModel.toggleTimer() },
        onOpenAnalytics = onOpenAnalytics
    )
}

@Composable
fun HomeScreenContent(
    state: HomeStates,
    onToggleTimer: () -> Unit,
    onOpenAnalytics: () -> Unit = {}
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
                color = White,
                style = MaterialTheme.typography.headlineMedium
            )

            BitTimerComponent(
                progress = state.progress,
                timerDisplay = state.timerDisplay,
                isRunning = state.isRunning,
                accentColor = state.accentColor,
                energyLevel = state.energyLevel
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
            
            Text(
                text = "View Insights",
                color = White.copy(alpha = 0.6f),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { onOpenAnalytics() },
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreenContent(
        state = HomeStates(),
        onToggleTimer = {}
    )
}
