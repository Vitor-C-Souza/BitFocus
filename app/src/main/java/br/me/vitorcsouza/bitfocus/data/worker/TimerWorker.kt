package br.me.vitorcsouza.bitfocus.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.me.vitorcsouza.bitfocus.domain.model.FocusSession
import br.me.vitorcsouza.bitfocus.domain.repository.FocusRepository
import br.me.vitorcsouza.bitfocus.utils.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TimerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repository: FocusRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val duration = inputData.getLong("duration", 0L)
        val goal = inputData.getString("goal") ?: "Focus Session"

        val session = FocusSession(
            startTime = System.currentTimeMillis() - (duration * 1000),
            endTime = System.currentTimeMillis(),
            duration = duration,
            goal = goal
        )

        repository.insertSession(session)

        NotificationHelper.showNotification(applicationContext, goal, duration)

        return Result.success()
    }
}
