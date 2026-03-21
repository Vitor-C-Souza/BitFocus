package br.me.vitorcsouza.bitfocus.ui.presentation.lab

data class LabStates(
    val totalBits: Int = 0,
    val streakDays: Int = 0,
    val totalHours: Double = 0.0,
    val weeklyTrend: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0),
    val isLoading: Boolean = true
)
