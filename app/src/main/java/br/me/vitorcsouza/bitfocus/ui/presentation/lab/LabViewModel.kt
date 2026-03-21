package br.me.vitorcsouza.bitfocus.ui.presentation.lab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.me.vitorcsouza.bitfocus.domain.model.FocusSession
import br.me.vitorcsouza.bitfocus.domain.repository.FocusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabViewModel @Inject constructor(
    private val repository: FocusRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LabStates())
    val state = _state.asStateFlow()

    init {
        fetchStats()
    }

    private fun fetchStats() {
        viewModelScope.launch {
            repository.getAllSessions().collect { sessions ->
                val totalMinutes = sessions.sumOf { it.duration } / 60
                val totalHours = totalMinutes / 60.0

                _state.update {
                    it.copy(
                        totalBits = sessions.size,
                        totalHours = totalHours,
                        streakDays = calculateStreak(sessions),
                        isLoading = false
                    )


                }
            }
        }
    }

    private fun calculateStreak(sessions: List<FocusSession>): Int {
        return if (sessions.isEmpty()) 0 else 12
    }
}