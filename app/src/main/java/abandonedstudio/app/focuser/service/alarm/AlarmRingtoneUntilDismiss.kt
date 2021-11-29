package abandonedstudio.app.focuser.service.alarm

import abandonedstudio.app.focuser.R
import abandonedstudio.app.focuser.helpers.service.ServiceUtil
import abandonedstudio.app.focuser.util.Constants
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmRingtoneUntilDismiss : LifecycleService(), AlarmService {

    @Inject
    lateinit var baseNotificationBuilder: NotificationCompat.Builder

    private lateinit var updatedNotificationBuilder: NotificationCompat.Builder

    private val alarmUri =
        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM) ?: RingtoneManager.getDefaultUri(
            RingtoneManager.TYPE_NOTIFICATION
        )

    private val ringtone = RingtoneManager.getRingtone(this, alarmUri)

    companion object {
        const val ACTION_START_ALARM = "ACTION_START_ALARM"
        const val ACTION_DISMISS = "ACTION_DISMISS"
    }

    override fun onCreate() {
        super.onCreate()

        updatedNotificationBuilder = baseNotificationBuilder
        updatedNotificationBuilder.apply {
            setContentTitle("Alarm")
            setSubText("Tap to dismiss")
            priority = NotificationCompat.PRIORITY_HIGH
            clearActions()
            addAction(
                R.drawable.ic_sharp_pause_24,
                applicationContext.getString(R.string.finish),
                PendingIntent.getService(
                    applicationContext,
                    1,
                    Intent(applicationContext, AlarmRingtoneUntilDismiss::class.java).apply {
                        action = ACTION_DISMISS
                    },
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    } else {
                        PendingIntent.FLAG_UPDATE_CURRENT
                    }
                )
            )
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when (it.action) {
                ACTION_START_ALARM -> {
                    startAlarmService()
                }
                ACTION_DISMISS -> {
                    stopAlarmService()
                }
                else -> {
                    Log.d("alarm", "unknown action")
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun startAlarmService() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                Constants.ALARM_SERVICE_NOTIFICATION_CHANNEL_ID,
                Constants.ALARM_SERVICE_NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        ServiceUtil.createNotificationChannel(channel, notificationManager)

        startForeground(Constants.ALARM_SERVICE_NOTIFICATION_ID, updatedNotificationBuilder.build())

        notificationManager.notify(
            Constants.ALARM_SERVICE_NOTIFICATION_ID,
            updatedNotificationBuilder.build()
        )
        ringtone.play()
    }

    private fun stopAlarmService() {
        ringtone.stop()
        stopSelf()
    }

    override fun startAlarm() {
        Intent(this, AlarmRingtoneUntilDismiss::class.java).also {
            it.action = ACTION_START_ALARM
            this.startService(it)
        }
    }

    override fun dismissAlarm() {
        Intent(this, AlarmRingtoneUntilDismiss::class.java).also {
            it.action = ACTION_DISMISS
            this.startService(it)
        }
    }
}
