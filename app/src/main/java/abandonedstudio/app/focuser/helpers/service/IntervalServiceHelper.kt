package abandonedstudio.app.focuser.helpers.service

import abandonedstudio.app.focuser.util.Constants
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.os.Build
import java.sql.Time
import java.util.concurrent.TimeUnit

object IntervalServiceHelper {
    const val ACTION_START_OT_RESUME_SERVICE = "START_OT_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "PAUSE_SERVICE"
    const val ACTION_END_SERVICE = "END_SERVICE"
    const val ACTION_SHOW_METHOD_FRAGMENT = "ACTION_SHOW_METHOD_FRAGMENT"
    const val ACTION_START_ALARM = "ACTION_START_ALARM"
    const val ACTION_DISMISS_ALARM = "ACTION_DISMISS_ALARM"

    const val FLAG_HOURS = "FLAG_HOURS"
    const val FLAG_MINUTES = "FLAG_MINUTES"
    const val FLAG_REPETITIONS = "FLAG_REPETITIONS"
    const val FLAG_BREAK_DURATION = "FLAG_BREAK_DURATION"

    fun createNotificationChanel(manager: NotificationManager) {
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                Constants.INTERVAL_SERVICE_NOTIFICATION_CHANNEL_ID,
                Constants.INTERVAL_SERVICE_NOTIFICATION_NAME,
                IMPORTANCE_LOW
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        ServiceUtil.createNotificationChannel(channel, manager)
    }

    fun remainingTimeMinutesFromMillisText(millis: Long): String {
        return "${TimeUnit.MILLISECONDS.toHours(millis)}:${TimeUnit.MILLISECONDS.toMinutes(millis) % 60}"
    }

    fun remainingTimeMinutesFromMillis(millis: Long): Int {
        return (TimeUnit.MILLISECONDS.toHours(millis) % 24 + TimeUnit.MILLISECONDS.toMinutes(millis) % 60).toInt()
    }
}
