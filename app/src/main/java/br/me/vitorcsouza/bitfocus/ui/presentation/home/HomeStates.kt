package br.me.vitorcsouza.bitfocus.ui.presentation.home

import androidx.compose.ui.graphics.Color
import br.me.vitorcsouza.bitfocus.ui.presentation.setup.EnergyLevel

data class HomeStates(
    val timerDisplay: String = "25:00",
    val progress: Float = 1f,
    val isRunning: Boolean = false,
    val currentGoal: String = "Coding Session",
    val sessionComplete: Boolean = false,
    val accentColor: Color = Color(0xFF00E5FF),
    val energyLevel: EnergyLevel = EnergyLevel.MID
)
