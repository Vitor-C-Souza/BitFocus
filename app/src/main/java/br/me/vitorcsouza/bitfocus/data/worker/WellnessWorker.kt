package br.me.vitorcsouza.bitfocus.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.me.vitorcsouza.bitfocus.utils.NotificationHelper

class WellnessWorker(
    context: Context,
    params: WorkerParameters
): CoroutineWorker(context, params) {

    companion object {
        val wellnessMessages = listOf(
            "Olhe para algo a 6 metros por 20 segundos para descansar a vista.",
            "Beba um copo de água agora.",
            "Levante-se e alongue os braços por 30 segundos.",
            "Respire fundo 5 vezes lentamente.",
            "Ajuste sua postura na cadeira.",
            "Pisque os olhos repetidamente por alguns segundos.",
            "Relaxe os ombros e o pescoço."
        )
    }

    override suspend fun doWork(): Result {
        val message = wellnessMessages.random()

        NotificationHelper.showWellnessNotification(
            context = applicationContext,
            message = message
        )

        return Result.success()
    }

}
