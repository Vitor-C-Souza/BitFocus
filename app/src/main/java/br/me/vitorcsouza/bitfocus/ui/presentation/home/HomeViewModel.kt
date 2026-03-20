package br.me.vitorcsouza.bitfocus.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.me.vitorcsouza.bitfocus.domain.model.FocusSession
import br.me.vitorcsouza.bitfocus.domain.usecase.home.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeStates())
    val state = _state.asStateFlow()

    private var timerJob: Job? = null
    private val totalSeconds = 25 * 60L

    fun toggleTimer() {
        if (_state.value.isRunning) {
            stopTimer()
        } else {
            startTimer(totalSeconds)
        }
    }

    private fun startTimer(durationSeconds: Long) {
        timerJob?.cancel()
        _state.update { it.copy(isRunning = true) }

        timerJob = viewModelScope.launch {
            var remainingTime = durationSeconds
            while (remainingTime >= 0) {
                val minutes = remainingTime / 60
                val seconds = remainingTime % 60
                _state.update {
                    it.copy(
                        timesDisplay = String.format("%02d:%02d", minutes, seconds),
                        progress = remainingTime.toFloat() / durationSeconds
                    )
                }

                if (remainingTime == 0L) {
                    onTimerFinished()
                    break
                }

                delay(1000)
                remainingTime--
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        _state.update { it.copy(isRunning = false) }
    }

    private fun onTimerFinished() {
        viewModelScope.launch {
            val session = FocusSession(
                startTime = System.currentTimeMillis() - (totalSeconds * 1000),
                endTime = System.currentTimeMillis(),
                duration = totalSeconds,
                goal = _state.value.currentGoal
            )
            homeUseCase.saveCompletedSession(session)
            _state.update { it.copy(sessionCompleted = true, isRunning = false) }
        }

    }
}
