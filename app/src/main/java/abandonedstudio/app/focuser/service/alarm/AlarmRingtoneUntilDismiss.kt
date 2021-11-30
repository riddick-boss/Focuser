package abandonedstudio.app.focuser.service.alarm

import abandonedstudio.app.focuser.R
import abandonedstudio.app.focuser.helpers.service.IntervalServiceHelper
import abandonedstudio.app.focuser.helpers.service.ServiceUtil
import abandonedstudio.app.focuser.service.IntervalService
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
import javax.inject.Inject

class AlarmRingtoneUntilDismiss @Inject constructor(context: Context) : Alarm {

    private val notificationRequestCode = 2

    private val notificationBuilder =
        NotificationCompat.Builder(context, Constants.ALARM_SERVICE_NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(true)
            .setOngoing(false)
            .setSmallIcon(R.drawable.ic_twotone_timer_24)
            .setContentTitle("Alarm")
            .addAction(
                R.drawable.ic_sharp_pause_24, context.getString(R.string.tap_to_dismiss), PendingIntent.getService(
                    context, notificationRequestCode, Intent(context, IntervalService::class.java).apply {
                        action = IntervalServiceHelper.ACTION_DISMISS_ALARM
                    },
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    } else {
                        PendingIntent.FLAG_UPDATE_CURRENT
                    }
                )
            )


    private val alarmUri =
        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM) ?: RingtoneManager.getDefaultUri(
            RingtoneManager.TYPE_NOTIFICATION
        )

    private val ringtone = RingtoneManager.getRingtone(context, alarmUri)

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel(
            Constants.ALARM_SERVICE_NOTIFICATION_CHANNEL_ID,
            Constants.ALARM_SERVICE_NOTIFICATION_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    init {
        ServiceUtil.createNotificationChannel(channel, notificationManager)
    }
//    override fun onCreate() {
//        super.onCreate()
//
//        updatedNotificationBuilder = baseNotificationBuilder
//
//        }
//    }

//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//
//        intent?.let {
//            when (it.action) {
//                ACTION_START_ALARM -> {
//                    startAlarmService()
//                }
//                ACTION_DISMISS -> {
//                    stopAlarmService()
//                }
//                else -> {
//                    Log.d("alarm", "unknown action")
//                }
//            }
//        }
//
//        return super.onStartCommand(intent, flags, startId)
//    }

    override fun startAlarm() {
        Log.d("alarm", "Alarm started")
        notificationManager.notify(
            Constants.ALARM_SERVICE_NOTIFICATION_ID,
            notificationBuilder.build()
        )
        ringtone.play()
    }

    override fun dismissAlarm() {
        Log.d("alarm", "Alarm dismissed")
        ringtone.stop()
        clearNotification()
    }

    private fun clearNotification(){
        notificationManager.cancel(notificationRequestCode)
    }
}
