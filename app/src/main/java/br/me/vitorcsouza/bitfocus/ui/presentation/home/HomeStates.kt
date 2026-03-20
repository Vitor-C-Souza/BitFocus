package br.me.vitorcsouza.bitfocus.ui.presentation.home

data class HomeStates(
    val timesDisplay: String = "25:00",
    val progress: Float = 1f,
    val isRunning: Boolean = false,
    val currentGoal: String = "Coding Session",
    val sessionCompleted: Boolean = false
)