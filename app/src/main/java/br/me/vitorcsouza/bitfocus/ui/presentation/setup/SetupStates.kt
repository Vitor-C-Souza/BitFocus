package br.me.vitorcsouza.bitfocus.ui.presentation.setup

enum class EnergyLevel { LOW, MID, HIGH }

data class SetupStates(
    val energyLevel: EnergyLevel = EnergyLevel.MID,
    val durationMinutes: Int = 25,
    val sessionGoal: String = "",
    val isLoading: Boolean = false
)
