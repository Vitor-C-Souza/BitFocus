package br.me.vitorcsouza.bitfocus.domain.usecase

import br.me.vitorcsouza.bitfocus.domain.repository.FocusRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map

class GetStatsUseCase @Inject constructor(
    private val repository: FocusRepository
){
    fun execute() = repository.getAllSessions().map { session ->
        val totalMinutes = session.sumOf { it.duration } /60
        val totalBits = session.size
        Pair(totalMinutes, totalBits)
    }
}