package br.me.vitorcsouza.bitfocus.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import br.me.vitorcsouza.bitfocus.MainActivity

object NotificationHelper {
    private const val CHANNEL_ID = "focus_channel"
    private const val WELLNESS_CHANNEL_ID = "wellness_channel"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Focus Timer",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificações de conclusão de foco"
            }
            manager.createNotificationChannel(channel)

            val wellnessChannel = NotificationChannel(
                WELLNESS_CHANNEL_ID,
                "Lembretes de Bem-estar",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Lembretes durante a sessão de foco"
            }
            manager.createNotificationChannel(wellnessChannel)
        }
    }

    fun showNotification(context: Context, goal: String, duration: Long = 25) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("navigate_to", "completion")
            putExtra("goal", goal)
            putExtra("duration", duration.toInt())
        }
        
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 
            0, 
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Sessão Finalizada!")
            .setContentText("Você concluiu: $goal. +1 Bit ganho!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        sendNotify(context, 1001, builder.build())
    }

    fun showWellnessNotification(context: Context, message: String) {
        val builder = NotificationCompat.Builder(context, WELLNESS_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
            .setContentTitle("BitFocus: Bem-estar")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        sendNotify(context, 2002, builder.build())
    }

    private fun sendNotify(context: Context, id: Int, notification: android.app.Notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }
        NotificationManagerCompat.from(context).notify(id, notification)
    }
}
