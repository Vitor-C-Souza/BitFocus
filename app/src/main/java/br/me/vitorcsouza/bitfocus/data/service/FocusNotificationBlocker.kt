package br.me.vitorcsouza.bitfocus.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.graphics.drawable.IconCompat.createFromIcon

class FocusNotificationBlocker : NotificationListenerService() {

    companion object {
        const val NOTIFICATION_ID = 999
        const val CHANNEL_ID = "focus_mode_channel"
        const val RELEASE_CHANNEL_ID = "released_notifications"
        const val ACTION_START_FOCUS = "br.me.vitorcsouza.bitfocus.ACTION_START_FOCUS"
        const val ACTION_STOP_FOCUS = "br.me.vitorcsouza.bitfocus.ACTION_STOP_FOCUS"
        
        var isFocusModeActive = false

        private val silencedNotifications = mutableListOf<StatusBarNotification>()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        Log.d("FocusBlocker", "onStartCommand action: $action, isFocusModeActive: $isFocusModeActive")

        when (action) {
            ACTION_START_FOCUS -> {
                isFocusModeActive = true
                synchronized(silencedNotifications) { silencedNotifications.clear() }
                startFocusForeground()
                updateInterruptionFilter(true)
            }
            ACTION_STOP_FOCUS -> {
                isFocusModeActive = false
                updateInterruptionFilter(false)
                releaseNotifications()
                stopForeground(true)
                stopSelf()
            }
            else -> {
                if (isFocusModeActive) {
                    startFocusForeground()
                }
            }
        }
        return START_STICKY
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (isFocusModeActive && sbn != null) {
            // Se a notificação for do próprio app BitFocus, não bloqueia
            if (sbn.packageName == packageName) {
                Log.d("FocusBlocker", "Ignorando notificação do próprio app.")
                return
            }

            synchronized(silencedNotifications) {
                silencedNotifications.add(sbn)
            }

            cancelNotification(sbn.key)
            Log.d("FocusBlocker", "Silenciada: ${sbn.packageName}. Total: ${silencedNotifications.size}")
        }
    }

    private fun releaseNotifications() {
        val listToRelease = synchronized(silencedNotifications) {
            val copy = silencedNotifications.toList()
            silencedNotifications.clear()
            copy
        }

        if (listToRelease.isEmpty()) return

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val packageManager = packageManager
        createChannels(notificationManager)

        listToRelease.forEach { sbn ->
            try {
                val original = sbn.notification
                val extras = original.extras

                val appName = try {
                    val appInfo = packageManager.getApplicationInfo(sbn.packageName, 0)
                    packageManager.getApplicationLabel(appInfo).toString()
                } catch (e: Exception) {
                    sbn.packageName
                }

                val title = extras.getCharSequence(Notification.EXTRA_TITLE)
                val text = extras.getCharSequence(Notification.EXTRA_TEXT) ?:
                extras.getCharSequence(Notification.EXTRA_BIG_TEXT)

                val builder = NotificationCompat.Builder(this, RELEASE_CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSubText(appName)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentIntent(original.contentIntent)
                    .setAutoCancel(true)
                    .setWhen(sbn.postTime)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                original.smallIcon?.let {
                    val iconCompat = createFromIcon(this, it)
                    if (iconCompat != null) builder.setSmallIcon(iconCompat)
                }

                val tag = sbn.tag ?: sbn.packageName
                notificationManager.notify(tag, sbn.id, builder.build())

            } catch (e: Exception) {
                Log.e("FocusBlocker", "Erro ao liberar: ${e.message}")
            }
        }
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d("FocusBlocker", "Listener Connected")
        if (isFocusModeActive) updateInterruptionFilter(true)
    }

    private fun updateInterruptionFilter(active: Boolean) {
        try {
            if (active) {
                requestInterruptionFilter(INTERRUPTION_FILTER_NONE)
            } else {
                requestInterruptionFilter(INTERRUPTION_FILTER_ALL)
            }
        } catch (e: Exception) {
            Log.e("FocusBlocker", "Filtro de interrupção: ${e.message}")
        }
    }

    private fun startFocusForeground() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createChannels(manager)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Modo Foco Ativo")
            .setContentText("Suas notificações aparecerão quando você terminar.")
            .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
            .setOngoing(true)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun createChannels(manager: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.getNotificationChannel(CHANNEL_ID) == null) {
                val serviceChan = NotificationChannel(CHANNEL_ID, "Serviço de Foco", NotificationManager.IMPORTANCE_LOW)
                manager.createNotificationChannel(serviceChan)
            }
            if (manager.getNotificationChannel(RELEASE_CHANNEL_ID) == null) {
                val releaseChan = NotificationChannel(RELEASE_CHANNEL_ID, "Notificações Silenciadas", NotificationManager.IMPORTANCE_DEFAULT)
                manager.createNotificationChannel(releaseChan)
            }
        }
    }

    override fun onDestroy() {
        Log.d("FocusBlocker", "onDestroy")
        if (isFocusModeActive) {
            releaseNotifications()
        }
        updateInterruptionFilter(false)
        super.onDestroy()
    }
}
