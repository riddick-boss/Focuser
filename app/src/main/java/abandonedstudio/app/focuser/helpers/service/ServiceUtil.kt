package abandonedstudio.app.focuser.helpers.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

object ServiceUtil {

    fun createNotificationChannel(channel: NotificationChannel, manager: NotificationManager){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(channel)
        }
//        TODO: add option below Android O
    }
}