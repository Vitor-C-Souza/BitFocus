package br.me.vitorcsouza.bitfocus.ui.presentation.lab

import br.me.vitorcsouza.bitfocus.domain.model.SessionCategory

data class CategoryStat(
    val category: SessionCategory,
    val totalMinutes: Long,
    val percentage: Float
)

data class LabStates(
    val totalBits: Int = 0,
    val streakDays: Int = 0,
    val totalHours: Double = 0.0,
    val weeklyTrend: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0),
    val categoryStats: List<CategoryStat> = emptyList(),
    val isLoading: Boolean = true
)
