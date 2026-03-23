package br.me.vitorcsouza.bitfocus.ui.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import br.me.vitorcsouza.bitfocus.data.service.FocusNotificationBlocker
import br.me.vitorcsouza.bitfocus.data.worker.TimerWorker
import br.me.vitorcsouza.bitfocus.domain.model.FocusSession
import br.me.vitorcsouza.bitfocus.domain.usecase.home.HomeUseCase
import br.me.vitorcsouza.bitfocus.ui.presentation.setup.EnergyLevel
import br.me.vitorcsouza.bitfocus.ui.theme.ElectricCyan
import br.me.vitorcsouza.bitfocus.ui.theme.SoftBlue
import br.me.vitorcsouza.bitfocus.ui.theme.VibrantPurple
import br.me.vitorcsouza.bitfocus.utils.NotificationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    @param:ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val initialDuration = savedStateHandle.get<Int>("duration") ?: 25
    private val initialGoal = savedStateHandle.get<String>("goal") ?: "Focus Session"
    private val initialEnergy = savedStateHandle.get<String>("energy") ?: "MID"

    private val energyLevel = try {
        EnergyLevel.valueOf(initialEnergy)
    } catch (e: Exception) {
        EnergyLevel.MID
    }

    private val energyColor = when(energyLevel) {
        EnergyLevel.LOW -> SoftBlue
        EnergyLevel.HIGH -> VibrantPurple
        EnergyLevel.MID -> ElectricCyan
    }

    private val _state = MutableStateFlow(
        HomeStates(
            timerDisplay = String.format(Locale.US, "%02d:00", initialDuration),
            currentGoal = initialGoal,
            accentColor = energyColor,
            energyLevel = energyLevel
        )
    )
    val state = _state.asStateFlow()

    private var timerJob: Job? = null
    private val totalSeconds = initialDuration * 60L

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
                        timerDisplay = String.format(Locale.US, "%02d:%02d", minutes, seconds),
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

        val workRequest = OneTimeWorkRequestBuilder<TimerWorker>()
            .setInitialDelay(durationSeconds, TimeUnit.SECONDS)
            .setInputData(
                workDataOf(
                "duration" to durationSeconds,
                "goal" to _state.value.currentGoal
            )
            )
            .addTag("timer_work")
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "timer_unique_work",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )

        val intent = Intent(context, FocusNotificationBlocker::class.java).apply {
            action = FocusNotificationBlocker.ACTION_START_FOCUS
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }



    private fun stopTimer() {
        timerJob?.cancel()
        _state.update { it.copy(isRunning = false) }

        val intent = Intent(context, FocusNotificationBlocker::class.java).apply {
            action = FocusNotificationBlocker.ACTION_STOP_FOCUS
        }
        context.startService(intent)

        WorkManager.getInstance(context).cancelUniqueWork("timer_unique_work")
    }

    private fun onTimerFinished() {
        WorkManager.getInstance(context).cancelUniqueWork("timer_unique_work")

        viewModelScope.launch {
            val session = FocusSession(
                startTime = System.currentTimeMillis() - (totalSeconds * 1000),
                endTime = System.currentTimeMillis(),
                duration = totalSeconds,
                goal = _state.value.currentGoal
            )
            NotificationHelper.showNotification(context, _state.value.currentGoal)
            homeUseCase.saveCompletedSession(session)

            _state.update { it.copy(sessionComplete = true, isRunning = false) }
        }

        val intent = Intent(context, FocusNotificationBlocker::class.java).apply {
            action = FocusNotificationBlocker.ACTION_STOP_FOCUS
        }
        context.startService(intent)
    }

    fun onNavigatedToCompletion() {
        _state.update { it.copy(sessionComplete = false) }
    }
}
