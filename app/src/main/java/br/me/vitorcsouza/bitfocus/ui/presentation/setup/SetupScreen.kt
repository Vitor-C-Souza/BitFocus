package br.me.vitorcsouza.bitfocus.ui.presentation.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.me.vitorcsouza.bitfocus.ui.components.BentoCard
import br.me.vitorcsouza.bitfocus.ui.components.BitFocusButton
import br.me.vitorcsouza.bitfocus.ui.theme.BorderGray
import br.me.vitorcsouza.bitfocus.ui.theme.DeepCharcoal
import br.me.vitorcsouza.bitfocus.ui.theme.ElectricCyan
import br.me.vitorcsouza.bitfocus.ui.theme.Periwinkle
import br.me.vitorcsouza.bitfocus.ui.theme.White

@Composable
fun SetupScreen(
    viewModel: SetupViewModel = hiltViewModel(),
    onStartSession: (Int, String, EnergyLevel) -> Unit
) {
    val state by viewModel.state.collectAsState()

    SetupContent(
        state = state,
        onEnergyLevelSelected = viewModel::onEnergyLevelSelected,
        onDurationChanged = viewModel::onDurationChanged,
        onGoalChanged = viewModel::onGoalChanged,
        onStartSession = onStartSession
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupContent(
    state: SetupStates,
    onEnergyLevelSelected: (EnergyLevel) -> Unit,
    onDurationChanged: (Int) -> Unit,
    onGoalChanged: (String) -> Unit,
    onStartSession: (Int, String, EnergyLevel) -> Unit
) {
    val scrollState = rememberScrollState()
    Scaffold(containerColor = DeepCharcoal) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Session Setup",
                    color = White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Configure your focus session",
                    color = Periwinkle,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            BentoCard {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Energy Level", color = White, style = MaterialTheme.typography.titleMedium)
                    Text("Match your current state", color = Periwinkle, style = MaterialTheme.typography.bodySmall)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                        .padding(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    EnergyLevel.entries.forEach { level ->
                        val isSelected = state.energyLevel == level
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .background(
                                    if (isSelected) ElectricCyan else Color.Transparent,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { onEnergyLevelSelected(level) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = level.name.lowercase().replaceFirstChar { it.uppercase() },
                                color = if (isSelected) DeepCharcoal else White,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            BentoCard {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Duration", color = White, style = MaterialTheme.typography.titleMedium)
                    Text("How long will you focus?", color = Periwinkle, style = MaterialTheme.typography.bodySmall)
                }

                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${state.durationMinutes}",
                        color = ElectricCyan,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "minutes",
                        color = Periwinkle,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Slider(
                        value = state.durationMinutes.toFloat(),
                        onValueChange = { onDurationChanged(it.toInt()) },
                        valueRange = 5f..120f,
                        thumb = {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .shadow(
                                        elevation = 8.dp,
                                        shape = CircleShape,
                                        spotColor = ElectricCyan,
                                        ambientColor = ElectricCyan
                                    )
                                    .background(ElectricCyan, CircleShape)
                            )
                        },
                        track = { sliderState ->
                            SliderDefaults.Track(
                                sliderState = sliderState,
                                modifier = Modifier.height(4.dp),
                                colors = SliderDefaults.colors(
                                    activeTrackColor = ElectricCyan,
                                    inactiveTrackColor = BorderGray
                                ),
                                thumbTrackGapSize = 0.dp
                            )
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("5", color = BorderGray, style = MaterialTheme.typography.labelSmall)
                        Text("60", color = BorderGray, style = MaterialTheme.typography.labelSmall)
                        Text("120", color = BorderGray, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            BentoCard {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Session Goal", color = White, style = MaterialTheme.typography.titleMedium)
                    Text("What are you working on?", color = Periwinkle, style = MaterialTheme.typography.bodySmall)
                }
                TextField(
                    value = state.sessionGoal,
                    onValueChange = { onGoalChanged(it) },
                    placeholder = { Text("e.g., Coding Session", color = BorderGray) },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = White,
                        unfocusedTextColor = White,
                        cursorColor = ElectricCyan,
                        focusedIndicatorColor = ElectricCyan,
                        unfocusedIndicatorColor = BorderGray
                    )
                )
            }

            BitFocusButton(
                text = "Start Focus",
                onClick = { onStartSession(state.durationMinutes, state.sessionGoal, state.energyLevel) }
            )
        }
    }
}

@Preview
@Composable
private fun SetupScreenPreview() {
    SetupContent(
        state = SetupStates(durationMinutes = 50, sessionGoal = ""),
        onEnergyLevelSelected = {},
        onDurationChanged = {},
        onGoalChanged = {},
        onStartSession = { _, _, _ -> }
    )
}
